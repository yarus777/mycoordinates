package com.example.mycoordinates;

import java.util.ArrayList;
import java.util.HashMap;





import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class MapActivity extends FragmentActivity {
	
	
	SQLiteDatabase db;
	DBHelper myDbHelper = new DBHelper(this);
	SupportMapFragment mapFragment;
	GoogleMap map;
	 
	ArrayList <HashMap<String, Object>> myCoords;
	
	TextView latitude;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

         
	}

	private void init() {
    }
	

	
	@Override
	  protected void onResume() {
	    super.onResume();
	    
	    
	    
	    myDbHelper.openDataBase();
	    db = myDbHelper.getWritableDatabase();
		 
     	 myCoords = new ArrayList<HashMap<String,Object>>();      
	        HashMap<String, Object> hm; 
	        
	        Cursor mCursor = db.query("mytable", null, null, null, null, null, "name");
		    mCursor.moveToFirst();
		    if (!mCursor.isAfterLast()) {
		     do {
		    	
		    	 hm = new HashMap<String, Object>();
		         
		      String name = mCursor.getString(1);
		      Double latitude = mCursor.getDouble(2);
		      Double longitude = mCursor.getDouble(3);
		      
		      
		      hm.put("name", name);
		      hm.put("latitude", latitude);
		      hm.put("longitude", longitude);
		      
		         
		      myCoords.add(hm);
		     } while (mCursor.moveToNext());
		    }
		    mCursor.close();

		    
		    mapFragment = (SupportMapFragment) getSupportFragmentManager()
	                .findFragmentById(R.id.map);

	            map = mapFragment.getMap();
	            if (map == null) {
	              finish();
	              return;
	            }
	            init();   
	            
	            map.clear();

	    Double lat,lng;
	    String name;
		    for (int i = 0; i < myCoords.size(); i++) {

		    lat = (Double) myCoords.get(i).get("latitude");
		    lng = (Double) myCoords.get(i).get("longitude");
	    	name = (String) myCoords.get(i).get("name");

	    	map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name));
	    	
	    }
		    
		    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(52.445278, 30.984167), 10);
		    map.animateCamera(cameraUpdate);
		    
	
		    
	}
	
	  
	  }

