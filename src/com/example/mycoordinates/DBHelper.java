package com.example.mycoordinates;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private SQLiteDatabase db;

    public DBHelper(Context context) {
      // ����������� �����������
      super(context, "myDB.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      // ������� ������� � ������
      db.execSQL("create table mytable ("
            + "id integer primary key autoincrement," 
            + "name text,"
             + "latitude text," 
              + "longitude text" + ");");

    }
    
    public void openDataBase() throws SQLException{
    	boolean dbExist = checkDataBase();
		if (dbExist==true) { 
        //��������� ��
        String myPath = "/data/data/com.example.mycoordinates/databases/" + "myDB.sql";
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
		}
    }
    
    private boolean checkDataBase() {
		SQLiteDatabase checkDb = null;
		try {
			String path = "/data/data/com.example.mycoordinates/databases/" + "myDB.sql";
			checkDb = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLException e) {
			Log.e(this.getClass().toString(), "Error while checking db");
		}
		//������� �� ����� ������ ��������, ��� ������ �����������
		if (checkDb != null) {
			checkDb.close();
		}
		return checkDb != null;
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
    	
    }
    
    
  }