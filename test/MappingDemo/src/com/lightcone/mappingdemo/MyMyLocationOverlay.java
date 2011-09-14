package com.lightcone.mappingdemo;

import android.content.Context;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

// This class subclasses (extends) MyLocationOverlay so that we can override its dispatchTap method
// to handle tap events on the present location dot.

public class MyMyLocationOverlay extends MyLocationOverlay {

	private Context context;

	public MyMyLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		this.context = context;   // Will need this for Toast argument below
	}
	
	// Override the dispatchTap() method to toggle the data display on and off when
	// the present location dot is tapped. Also display a short Toast (transient message) to the
	// user indicating the display status change.
	
	@Override
	protected boolean dispatchTap(){
		if(DisplayOverlay.showData){ 
			Toast.makeText(context, "Suppressing data readout", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context,"Display data readout", Toast.LENGTH_SHORT).show();
		}
		// Toggle the GPS data display
		DisplayOverlay.showData = ! DisplayOverlay.showData;
		return true;
	}
}
