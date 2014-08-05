package com.example.mycoordinates;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TabHost tabHost = getTabHost();
        
        
        TabHost.TabSpec tabSpec;
        
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.marker));
        tabSpec.setContent(new Intent(this, CoordsActivity.class));
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.list));
        tabSpec.setContent(new Intent(this, ListActivity.class));
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.map));
        tabSpec.setContent(new Intent(this, MapActivity.class));
        tabHost.addTab(tabSpec);


    }


   

}
