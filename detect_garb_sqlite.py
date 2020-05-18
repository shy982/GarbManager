from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
import time
import numpy as np
import tensorflow as tf
import sqlite3
conn = sqlite3.connect('C:\sqlite\specter.db')
c = conn.cursor()
def load_graph(model_file):
  graph = tf.Graph()
  graph_def = tf.GraphDef()

  with open(model_file, "rb") as f:
    graph_def.ParseFromString(f.read())
  with graph.as_default():
    tf.import_graph_def(graph_def)

  return graph

def read_tensor_from_image_file(file_name, input_height=299, input_width=299,
				input_mean=0, input_std=255):
  input_name = "file_reader"
  file_reader = tf.read_file(file_name, input_name)
  if file_name.endswith(".png"):
    image_reader = tf.image.decode_png(file_reader, channels = 3,
                                       name='png_reader')
  elif file_name.endswith(".gif"):
    image_reader = tf.squeeze(tf.image.decode_gif(file_reader,
                                                  name='gif_reader'))
  elif file_name.endswith(".bmp"):
    image_reader = tf.image.decode_bmp(file_reader, name='bmp_reader')
  else:
    image_reader = tf.image.decode_jpeg(file_reader, channels = 3,
                                        name='jpeg_reader')
  float_caster = tf.cast(image_reader, tf.float32)
  dims_expander = tf.expand_dims(float_caster, 0);
  resized = tf.image.resize_bilinear(dims_expander, [input_height, input_width])
  normalized = tf.divide(tf.subtract(resized, [input_mean]), [input_std])
  sess = tf.Session()
  result = sess.run(normalized)

  return result

def load_labels(label_file):
  label = []
  proto_as_ascii_lines = tf.gfile.GFile(label_file).readlines()
  for l in proto_as_ascii_lines:
    label.append(l.rstrip())
  return label

if __name__ == "__main__":
  input_height = 224
  input_width = 224
  input_mean = 128
  input_std = 128
  input_layer = "input"
  output_layer = "final_result"
  model_file = r"C:\Users\SPECTER\Desktop\GARBAGE DETECTION PROJECT\CCTV-Street-Garbage-Detection-master\files\retrained_graph.pb"
  label_file = r"C:\Users\SPECTER\Desktop\GARBAGE DETECTION PROJECT\CCTV-Street-Garbage-Detection-master\files\retrained_labels.txt"
  for num in range(1,10):
      cctv_num = -1
      pct_unclean = 0
      file_name = r"C:\Users\SPECTER\Desktop\GARBAGE DETECTION PROJECT\garbdata\check"+str(num)+".jpg"
      graph = load_graph(model_file)
      t = read_tensor_from_image_file(file_name,
                                      input_height=input_height,
                                      input_width=input_width,
                                      input_mean=input_mean,
                                      input_std=input_std)
    
      input_name = "import/" + input_layer
      output_name = "import/" + output_layer
      input_operation = graph.get_operation_by_name(input_name);
      output_operation = graph.get_operation_by_name(output_name);
    
      with tf.Session(graph=graph) as sess:
        start = time.time()
        results = sess.run(output_operation.outputs[0],
                          {input_operation.outputs[0]: t})
        end=time.time()
      results = np.squeeze(results)
    
      top_k = results.argsort()[-5:][::-1]
      labels = load_labels(label_file)
    
      print('\nEvaluation time: {:.3f}s\n'.format(end-start))
      template = "{} (score={:0.5f})"
      for i in top_k:
#        print(template.format(labels[i], results[i]))
        if labels[i] == "not clean":
            pct_unclean = results[i]*100
            cctv_num = (num%2)+1
      print("Image Number",num)
      print("CCTV Camera number ",cctv_num)
      print("Garbage % ",pct_unclean)
      c.execute("""INSERT INTO garb VALUES(?, ?)""",(cctv_num,float(pct_unclean)))
      conn.commit()