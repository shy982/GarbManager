# GarbManager
## Intelligent City Waste Monitoring App

This is an Android app through which municipality/sanitation organizations can remotely monitor
areas for garbage aggregation by receiving alerts of the percentage garbage levels in each area using
the CCTV camera footage. Performed transfer learning on a VGG-16 Network trained over the COCO
dataset for the garbage detection and updated an internal database maintained in SQLite3 for tracking
the cleanliness level.

Designed the App with Android Studio. The Images of lanes/streets are taken by CCTV cameras and sent to a database periodically, the model uses these images and predicts the percentage of garbage detected in each image. When the accumulated percentage exceeds a threshold, the muncipal authorities get alerted automatically through the app. The user can also individually view each area's garbage percentage. 

Saves time and helps prioritize areas to clean up. Classifies areas based on levels of contamination. Improves efficiency and reduces waste of man power to constantly go to each area to collect waste, irrespective of whether there is garbage in that area or not. 
