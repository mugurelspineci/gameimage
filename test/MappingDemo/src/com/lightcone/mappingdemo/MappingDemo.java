package com.lightcone.mappingdemo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.util.Log;

public class MappingDemo extends Activity implements OnClickListener {
	
	public double lat;
	public double lon;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     // Add Click listeners for all buttons
        View firstButton = findViewById(R.id.geocode_button);
        firstButton.setOnClickListener(this);
        View secondButton = findViewById(R.id.latlong_button);
        secondButton.setOnClickListener(this);
        View thirdButton = findViewById(R.id.presentLocation_button);
        thirdButton.setOnClickListener(this);   
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.geocode_button:
					
				// Following adapted from Conder and Darcey, pp.321 ff.		
				EditText placeText = (EditText) findViewById(R.id.geocode_input);			
				String placeName = placeText.getText().toString();
				// Break from execution if the user has not entered anything in the field
				if(placeName.compareTo("")==0) break;
				int numberOptions = 5;
				String [] optionArray = new String[numberOptions];
				Geocoder gcoder = new Geocoder(this);
				
				// Note that the Geocoder uses synchronous network access, so in a serious application
				// it would be best to put it on a background thread to prevent blocking the main UI if network
				// access is slow. Here we are just giving an example of how to use it so, for simplicity, we
				// don't put it on a separate thread.
				
				try{
					List<Address> results = gcoder.getFromLocationName(placeName,numberOptions);
					Iterator<Address> locations = results.iterator();
					String raw = "\nRaw String:\n";
					String country;
					int opCount = 0;
					while(locations.hasNext()){
						Address location = locations.next();
						lat = location.getLatitude();
						lon = location.getLongitude();
						country = location.getCountryName();
						if(country == null) {
							country = "";
						} else {
							country =  ", "+country;
						}
						raw += location+"\n";
						optionArray[opCount] = location.getAddressLine(0)+", "+location.getAddressLine(1)
							+country+"\n";
						opCount ++;
					}
					Log.i("Location-List", raw);
					Log.i("Location-List","\nOptions:\n");
					for(int i=0; i<opCount; i++){
						Log.i("Location-List","("+(i+1)+") "+optionArray[i]);
					}
					
				} catch (IOException e){
					Log.e("Geocoder", "I/O Failure; is network available?",e);
				}
				
				Intent j = new Intent(this, ShowTheMap.class);
				ShowTheMap.putLatLong(lat, lon);
				startActivity(j);
				break;
			
			case R.id.latlong_button:
				
				// Read the latitude and longitude from the input fields
		        EditText latText = (EditText) findViewById(R.id.lat_input);
		        EditText lonText = (EditText) findViewById(R.id.lon_input);	
				String latString = latText.getText().toString();
				String lonString = lonText.getText().toString();
				// Only execute if user has put entries in both lat and lon fields.
				if(latString.compareTo("") != 0 && lonString.compareTo("") != 0){
					lat = Double.parseDouble(latString);
					lon = Double.parseDouble(lonString);
					
					Intent k = new Intent(this, ShowTheMap.class);
					ShowTheMap.putLatLong(lat,lon);
					startActivity(k);
				}
				break;
			
			case R.id.presentLocation_button:
				Intent m = new Intent(this, MapMe.class);
				startActivity(m);
				break;	
		}	
	}
	
	// To create menu that pops up when Menu button is pressed (Options menu).
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		int groupId = 0;
		int menuItemOrder = Menu.NONE;
		// Create menu ids for the event handler to reference
		int menuItemId1 = Menu.FIRST;
		int menuItemId2 = Menu.FIRST+1;
		int menuItemId3 = Menu.FIRST+2;
		// Create menu text
		int menuItemText1 = R.string.quit;
		int menuItemText2 = R.string.help_title;
		int menuItemText3 = R.string.settings;
		// Add the items to the menu
		MenuItem menuItem1 = menu.add(groupId, menuItemId1, menuItemOrder, menuItemText1)
			.setIcon(R.drawable.ic_menu_close_clear_cancel);
		MenuItem menuItem2 = menu.add(groupId, menuItemId2, menuItemOrder, menuItemText2)
			.setIcon(R.drawable.ic_menu_help);
		MenuItem menuItem3 = menu.add(groupId, menuItemId3, menuItemOrder, menuItemText3)
			.setIcon(R.drawable.ic_menu_preferences);
		return true;
	}
	
	// Handle events from the popup menu above
	
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case (Menu.FIRST):
				finishUp();
				return true;
			case (Menu.FIRST+1):
				// Actions for help page
				Intent i = new Intent(this, Help.class);
				startActivity(i);
				return true;
			case(Menu.FIRST+2):
				// Actions for settings page
				Intent j = new Intent(this, Prefs.class);
				startActivity(j);
				return true;
		
		}
		return false;
	}
	
	// Method to quit   
    public void finishUp(){
    	finish();
    }
}