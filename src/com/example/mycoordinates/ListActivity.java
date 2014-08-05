package com.example.mycoordinates;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends Activity  implements OnClickListener{
	
	SQLiteDatabase db;
	CheckboxAdapter listItemAdapter; 
	Button btn;
	ArrayList <HashMap<String, Object>> myCoords;
	DBHelper myDbHelper = new DBHelper(this);
	final int DIALOG = 1;
	Dialog dialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

		

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
		      String latitude = mCursor.getString(2);
		      String longitude = mCursor.getString(3);
		      String id = mCursor.getString(0);
		      
		      
		      hm.put("name", name);
		      hm.put("latitude", latitude);
		      hm.put("longitude", longitude);
		      hm.put("selected", false);
		      hm.put("id", id);
		      
		         
		      myCoords.add(hm);
		     } while (mCursor.moveToNext());
		    }
		    mCursor.close();
	        
	        ListView lv = (ListView)findViewById(R.id.listView1);
	         
		    
	        listItemAdapter = new CheckboxAdapter(this, myCoords);  
	        lv.setAdapter(listItemAdapter);
   
	  }


	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
	      
	    case R.id.button1:
	    	showDialog(DIALOG);
	    
	    	  	
		break;
		
		}
		
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
      if (id == DIALOG) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Вы уверены, что хотите удалить выбранные записи?");
        adb.setPositiveButton("Да", new DialogInterface.OnClickListener() {
        	
        	public void onClick(DialogInterface dialog, int whichButton) {
        		
        		db = myDbHelper.getWritableDatabase();
        		
        		HashMap<Integer, Boolean> state1 =listItemAdapter.state;
    	    	
    	    	for(int i=listItemAdapter.getCount()-1;i>=0;i--){
    	    	if(state1.get(i)!=null){
    	    		@SuppressWarnings("unchecked")  
    		        HashMap<String, Object> map1=(HashMap<String, Object>) listItemAdapter.getItem(i);  
    	    		String id1=map1.get("id").toString();  
    		        myCoords.remove(i);
    		        state1.remove(i);
    		        db.delete("mytable", "id = " + id1, null);
    		       
    	    	}
    	    	 listItemAdapter.notifyDataSetChanged();
    	    	}
             
        	  }
        	});
        adb.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
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





}
