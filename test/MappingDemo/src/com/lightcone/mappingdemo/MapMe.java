package com.lightcone.mappingdemo;

import java.util.List;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapMe extends MapActivity implements LocationListener {
	
	private static double lat;
	private static double lon;
	private MapController mapControl;
	private MapView mapView;
	LocationManager locman;
    Location loc;
    String provider = LocationManager.GPS_PROVIDER;
    String TAG = "GPStest";
	Bundle locBundle;
	private int numberSats = -1;
	private float satAccuracy = 2000;
	private float bearing;
	private double altitude;
	private float speed;
	private String currentProvider;
	
	// Following 2 parameters control how often the GPS is called to update location.
	// These are only suggestions to the hardware. Precision versus power-consumption 
	// considerations govern what these settings should be.  The defaults can be reset
	// in the preferences (settings) menu. (See the updateGPSprefs method below.)
	
	long GPSupdateInterval;     // In milliseconds
	float GPSmoveInterval;      // In meters
	
	private MyMyLocationOverlay myLocationOverlay; 
	private List<Overlay> mapOverlays;
	public DisplayOverlay displayOverlay;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar to give more space
        setContentView(R.layout.mapme); 
        
        updateGPSprefs();
        
        // Set up location manager for determining present location of phone
 	    locman = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
 	    
	 	// Listener for GPS Status...	
 	    final GpsStatus.Listener onGpsStatusChange = new GpsStatus.Listener(){
		 	public void onGpsStatusChanged(int event){
			 	switch(event){
				 	case GpsStatus.GPS_EVENT_STARTED:
				 		// Started...
				 	break;
				 	case GpsStatus.GPS_EVENT_FIRST_FIX:
				 		// First Fix...
				 		Toast.makeText(MapMe.this, "GPS has First fix", Toast.LENGTH_LONG).show();
				 	break;
				 	case GpsStatus.GPS_EVENT_STOPPED:
				 		// Stopped...
				 	break;
				 	case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				 		// Satellite update
				 	break;
			 	}
			 	GpsStatus status = locman.getGpsStatus(null);
			 	
			 	// Not presently doing anything with following status list for individual satellites	
			 	Iterable<GpsSatellite> satlist = status.getSatellites();
		 	}
	 	};

	 	locman.addGpsStatusListener(onGpsStatusChange);
	    locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);  
	    Log.i(TAG, locman.toString());
	    
	    // Add map controller with zoom controls
        mapView = (MapView) findViewById(R.id.mv2);
        mapView.setSatellite(false);
        mapView.setTraffic(false);
        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
        int maxZoom = mapView.getMaxZoomLevel();
        int initZoom = (int)(0.95*(double)maxZoom);
        mapControl = mapView.getController();
        mapControl.setZoom(initZoom);
        
        // Set up compass and dot for present location map overlay
        List<Overlay> overlays = mapView.getOverlays();
        myLocationOverlay = new MyMyLocationOverlay(this, mapView);
        overlays.add(myLocationOverlay);
        
        // Set up overlay for data display
        displayOverlay = new DisplayOverlay();
        mapOverlays = mapView.getOverlays();
        mapOverlays.add(displayOverlay);
	}
	
	// This sets the s key on the phone to toggle between satellite and map view
	// and the t key to toggle between traffic and no traffic view (traffic view
	// relevant only in urban areas where it is reported). (See Murphy, pp. 304-305)
	// The map/satellite and traffic/no traffic toggles are independent so there are
	// four combinations.
	
	public boolean onKeyDown(int keyCode, KeyEvent e){
		if(keyCode == KeyEvent.KEYCODE_S){
			mapView.setSatellite(!mapView.isSatellite());
			return true;
		} else if(keyCode == KeyEvent.KEYCODE_T){
			mapView.setTraffic(!mapView.isTraffic());
			centerOnLocation();  // To ensure change displays immediately
		}
		return(super.onKeyDown(keyCode, e));
	}
	
	// Required method since class extends MapActivity
	@Override
	protected boolean isRouteDisplayed() {
		return false;  // Don't display a route
	}

	// Required method since class implements LocationListener interface
	@Override
	public void onLocationChanged(Location location) {
		// Called when location has changed
		centerOnLocation();
	}

	// Required method since class implements LocationListener interface
	@Override
	public void onProviderDisabled(String provider) {
		// Called when user disables the location provider. If 
		// requestLocationUpdates is called on an already disabled 
		// provider, this method is called immediately.
		
		locman.removeUpdates(this);	
	}

	// Required method since class implements LocationListener interface
	@Override
	public void onProviderEnabled(String provider) {
		// Called when the user enables the location provider
		locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);
	}

	// Required method since class implements LocationListener interface
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Called when the provider status changes. This method is called 
		// when a provider is unable to fetch a location or if the provider 
		// has recently become available after a period of unavailability.
		
		centerOnLocation();	
	}
	
    // Method to query phone location and center map on that location
    private void centerOnLocation() {
    	loc = locman.getLastKnownLocation(provider);
    	if(loc != null){
    		lat = loc.getLatitude();
   	    	lon = loc.getLongitude();
	    	Log.i(TAG+"_loc",loc.toString());   
	    	Log.i(TAG, "Lat="+lat+" lon="+lon);
	    	GeoPoint newPoint = new GeoPoint((int)(lat*1e6),(int)(lon*1e6)); 	   
	    	mapControl.animateTo(newPoint);
	    	getSatelliteData();
	    	if(displayOverlay != null){
	    		displayOverlay.putSatStuff(lat, lon, satAccuracy, bearing, altitude,
	    				speed, currentProvider, numberSats);
	    	}
    	}
    }
    
    // Method to determine the number of satellites contributing to the fix and
    // various other quantities like speed and altitude
    
    public void getSatelliteData(){
    	if(loc != null){
    		
    		// Determine number of satellites used for the fix
	   	    locBundle = loc.getExtras();
	   	    if(locBundle != null){ 
	   	    	numberSats = locBundle.getInt("satellites",-1);
	   	    	//Log.i(TAG+"locBundle",locBundle.toString());
	   		    //Log.i(TAG+"locBundle","keySet: "+locBundle.keySet());
	   	    	Log.i(TAG+"locBundle","satellites: "+numberSats);
	   	    }
	   	    // Following return 0 if the corresponding boolean (e.g., hasAccuracy) are false.
			satAccuracy = loc.getAccuracy();
			bearing = loc.getBearing();
			altitude = loc.getAltitude();
			speed = loc.getSpeed();
    	}
    }
    
	// OnPause() and onResume() methods to handle when app is forced to background by another
	// process and then resumed. Generally should release resources not needed while in background
	// and restore when resumed. For example, in the following locman.removeUpdates(this) when 
	// pausing removes the request for GPS updates when MapMe goes into the background. This
	// permits the GPS engine to shut down (if it is not being used by another program), which saves
	// a lot of power. If MapMe is resumed (moved from background to foreground), the onResume()
	// method resumes GPS update requests through the locman.requestLocationUpdates() method.
	
	public void onPause() {
		super.onPause();
		Log.i(TAG,"******   MapMe pausing: Removing GPS update requests to save power");
		if(Prefs.getCompass(getApplicationContext())) myLocationOverlay.disableCompass();
        myLocationOverlay.disableMyLocation();
		locman.removeUpdates(this);
	}
	
	public void onResume(){
		super.onResume();
        // Check for GPS prefs and set parameters accordingly
		updateGPSprefs();
	 	locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);	
	 	if(Prefs.getCompass(getApplicationContext())) myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        Log.i(TAG,"******  MapMe restarting: Resuming GPS update requests."+
				" GPSUpdateInterval="+GPSupdateInterval+"ms GPSmoveInterval="+GPSmoveInterval+" m");
	}
	
	// Method to assign GPS prefs
	public void updateGPSprefs(){
		int gpsPref = Integer.parseInt(Prefs.getGPSPref(getApplicationContext()));
		switch(gpsPref){
		case 1:
			GPSupdateInterval = 5000;  // milliseconds
			GPSmoveInterval = 1;       // meters
			break;
		case 2:
			GPSupdateInterval = 10000;
			GPSmoveInterval = 100;
			break;
		case 3:
			GPSupdateInterval = 125000;
			GPSmoveInterval = 1000;
			break;
		}	
	}
}
