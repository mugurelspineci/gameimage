package com.lightcone.mappingdemo;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class ShowTheMap extends MapActivity {
	
	private static double lat;
	private static double lon;
	private int latE6;
	private int lonE6;
	private MapController mapControl;
	private GeoPoint gp;
	private MapView mapView;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar to give more space
        setContentView(R.layout.showthemap);
        
        // Add map controller with zoom controls
        mapView = (MapView) findViewById(R.id.mv);
        mapView.setSatellite(true);
        mapView.setTraffic(false);
        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
        int maxZoom = mapView.getMaxZoomLevel();
        int initZoom = (int)(0.80*(double)maxZoom);
        mapControl = mapView.getController();
        mapControl.setZoom(initZoom);
        // Convert lat/long in degrees into integers in microdegrees
        latE6 =  (int) (lat*1e6);
        lonE6 = (int) (lon*1e6);
        gp = new GeoPoint(latE6, lonE6);
        mapControl.animateTo(gp);    
	}
	
	// Method to insert latitude and longitude in degrees
	public static void putLatLong(double latitude, double longitude){
		lat = latitude;
		lon = longitude;
	}
	
	// This sets the s key on the phone to toggle between satellite and map view
	// and the t key to toggle between traffic and no traffic view (traffic view
	// relevant only in urban areas where it is reported). (See Murphy, pp. 304-305)
	// The map/satellite and traffic/no traffic toggles are independent so there are
	// four combinations.
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e){
		if(keyCode == KeyEvent.KEYCODE_S){
			mapView.setSatellite(!mapView.isSatellite());
			return true;
		} else if(keyCode == KeyEvent.KEYCODE_T){
			mapView.setTraffic(!mapView.isTraffic());
			mapControl.animateTo(gp);  // To ensure change displays immediately
		}
		return(super.onKeyDown(keyCode, e));
	}
	
	// Required method since class extends MapActivity
	@Override
	protected boolean isRouteDisplayed() {
		return false;  // Don't display a route
	}

}
