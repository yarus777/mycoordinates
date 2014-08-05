package com.example.mycoordinates;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckboxAdapter extends BaseAdapter {  
	  
	Context context;  
	ArrayList<HashMap<String, Object>> listData;  
	
	HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();  
	  
	
	public CheckboxAdapter(Context context,ArrayList<HashMap<String,Object>> listData) {  
	this.context = context;  
	this.listData = listData;  
	}  
	  
	@Override  
	public int getCount() {  

	return listData.size();  
	}  
	  
	@Override  
	public Object getItem(int position) {  

	return listData.get(position);  
	}  
	  
	@Override  
	public long getItemId(int position) {  

	return position;  
	}  
	  

	@Override  
	public View getView(final int position, View convertView, ViewGroup parent) {  

	  
	LayoutInflater mInflater = LayoutInflater.from(context);  
	convertView = mInflater.inflate(R.layout.item1, null);  

	TextView name = (TextView) convertView.findViewById(R.id.name);  
	name.setText((String) listData.get(position).get("name")); 
	
	TextView latitude = (TextView) convertView.findViewById(R.id.latitude);  
	latitude.setText((String) listData.get(position).get("latitude"));  
	
	TextView longitude = (TextView) convertView.findViewById(R.id.longitude);  
	longitude.setText((String) listData.get(position).get("longitude")); 
	
	TextView id = (TextView) convertView.findViewById(R.id.id);  
	id.setText((String) listData.get(position).get("id"));
	
	CheckBox check = (CheckBox) convertView.findViewById(R.id.selected);  
	check.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
	
	@Override  
	public void onCheckedChanged(CompoundButton buttonView,  
	boolean isChecked) {  

	if (isChecked) {  
	state.put(position, isChecked);  
	} else {  
	state.remove(position);  
	}  
	}  
	});  
	check.setChecked((state.get(position) == null ? false : true));  
	return convertView;  
	}  
	}  
