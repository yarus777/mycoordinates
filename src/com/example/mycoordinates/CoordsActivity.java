package com.example.mycoordinates;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CoordsActivity extends Activity implements OnClickListener {
	
	TextView Latitude, Longitude, Accuracy, Altitude,text;
	Button SaveLocation, ShareLocation;
	double lat,lng;
    int acc, alt;
    private LocationManager locationManager;
    final int DIALOG = 1;
	Dialog dialog;
	DBHelper dbHelper;
	SQLiteDatabase db;
	String value;
	
	final String LOG_TAG = "myLogs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coords);
		
		Latitude = (TextView) findViewById(R.id.Latitude);
        Longitude = (TextView) findViewById(R.id.Longitude);
        Accuracy = (TextView) findViewById(R.id.Accuracy);
        Altitude = (TextView) findViewById(R.id.Altitude);
        SaveLocation = (Button) findViewById(R.id.SaveLocation);

        SaveLocation.setOnClickListener(this);
        
        ShareLocation = (Button) findViewById(R.id.ShareLocation);

        ShareLocation.setOnClickListener(this);
        
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        dbHelper = new DBHelper(this);

	}

	@Override
	public void onClick(View v) {
		
		db = dbHelper.getWritableDatabase();
		
		switch (v.getId()) {
	    case R.id.SaveLocation:
	    	
	      showDialog(DIALOG);
	      break;
	    case R.id.ShareLocation:
	    	Intent sendIntent = new Intent(Intent.ACTION_SEND);	
	    	sendIntent.setType("text/plain"); 
	    	
	    	sendIntent.putExtra(Intent.EXTRA_TEXT, "Мои координаты: широта - "+Latitude.getText()+", долгота - "+Longitude.getText());    	
	    	sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Мое местоположение");
	    	sendIntent=Intent.createChooser(sendIntent, "Выбор приложения");
	    	startActivity(sendIntent);
	      break;
	   
	    }
		
	}
	
	
	@Override
    protected Dialog onCreateDialog(int id) {
      if (id == DIALOG) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Введите название местоположения");
        final EditText input = new EditText(this);
        adb.setView(input);
        adb.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
        	
        	public void onClick(DialogInterface dialog, int whichButton) {
        		
        	  value = input.getText().toString();
        	  
              ContentValues cv = new ContentValues();
              
              String latitude = Latitude.getText().toString();
              String longitude = Longitude.getText().toString();

              db = dbHelper.getWritableDatabase();

                cv.put("name", value);
                cv.put("latitude",latitude);
                cv.put("longitude", longitude);
                
               long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                Toast.makeText(CoordsActivity.this, "Запись успешно сохранена", Toast.LENGTH_LONG).show();
     
              //dbHelper.close();     	  
             
        	  }
        	});
        adb.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
        	  public void onClick(DialogInterface dialog, int whichButton) {
        	  }
        	});
        dialog = adb.create();

        dialog.setOnShowListener(new OnShowListener() {
          public void onShow(DialogInterface dialog) {
          }
        });

        dialog.setOnCancelListener(new OnCancelListener() {
          public void onCancel(DialogInterface dialog) {
          }
        });

        dialog.setOnDismissListener(new OnDismissListener() {
          public void onDismiss(DialogInterface dialog) {
          }
        });
        return dialog;
      }
      return super.onCreateDialog(id);
    }
	
	 @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
	        1000 * 10, 10, locationListener);
	    locationManager.requestLocationUpdates(
	        LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
	        locationListener);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(locationListener);
	  }
	  
	  private LocationListener locationListener = new LocationListener() {

	      @Override
	      public void onLocationChanged(Location location) {
	        showLocation(location);
	      }

	      @Override
	      public void onProviderDisabled(String provider) {
	      }

	      @Override
	      public void onProviderEnabled(String provider) {
	        showLocation(locationManager.getLastKnownLocation(provider));
	      }
	      
	      @Override
	      public void onStatusChanged(String provider, int status, Bundle extras) {
	        
	      }
	    };
	  
	    private void showLocation(Location location) {
	        if (location == null)
	          return;
	        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
	        
	        	    lat = (double)Math.round(location.getLatitude() * 1000000) / 1000000;
	        		lng = (double)Math.round(location.getLongitude() * 1000000) / 1000000;
	        		alt = (int)Math.round(location.getAltitude());
	        		acc = (int)Math.round(location.getAccuracy());
	  
	        		Latitude.setText(Double.toString(lat));
	        		Longitude.setText(Double.toString(lng));
	        		Altitude.setText(Integer.toString(alt)+" м");
	        		Accuracy.setText(Integer.toString(acc)+" м");

	        		
	        } else if (location.getProvider().equals(
	            LocationManager.NETWORK_PROVIDER)) {
	                 
	        lat = (double)Math.round(location.getLatitude() * 1000000) / 1000000;
	  		lng = (double)Math.round(location.getLongitude() * 1000000) / 1000000;  
	  		alt = (int)Math.round(location.getAltitude());
	  		acc = (int)Math.round(location.getAccuracy());
	  		
	  		Latitude.setText(Double.toString(lat));
	  		Longitude.setText(Double.toString(lng));
	  		Altitude.setText(Integer.toString(alt)+" м");
   		Accuracy.setText(Integer.toString(acc)+" м");

	        }
	      }

	

}
