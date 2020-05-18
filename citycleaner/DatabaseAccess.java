package com.example.citycleaner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public String getAddress(String Place) {
        c = db.rawQuery("select pct from garb where place = '"+Place+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String address =c.getString(0);
            buffer.append(""+address);
        }
        return buffer.toString();

    }


}
