package com.lightcone.mappingdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class Prefs extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		// Register a change listener 
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	// Inherited abstract method so it must be implemented
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {	
		Log.i("Preferences", "Preferences changed, key="+key);
		if(key.compareTo("editTextPref")==0)
		Log.i("Preferences", " Changed name to " + Prefs.getTitle(getApplicationContext()));
	}
	
	// Static method to return the preference for whether to display compass
	public static boolean getCompass(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("compass", true);
	}
	
	// Static method to return the preference for the GPS precision setting
	public static String getGPSPref(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getString("gpsPref", "1");
	}
	
	// Static method to return the preference for the name (only used for demonstration)
	public static String getTitle(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getString("editTextPref", "");
	}
}
