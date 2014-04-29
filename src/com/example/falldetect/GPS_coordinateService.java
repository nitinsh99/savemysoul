package com.example.falldetect;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.widget.Toast;

public class GPS_coordinateService extends Service implements LocationListener{
	WakeLock wakelock; 
	LocationManager locationmanager;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	private boolean isGPSEnabled=false;
	private boolean isNetworkEnabled=false;
	public static String latitude;
	public static String longitude;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		 super.onCreate();

			PowerManager mgr=(PowerManager)this.getSystemService(Context.POWER_SERVICE);
			this.wakelock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getName());
			this.wakelock.acquire();
		    locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		    locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0,this); 
		    Toast.makeText(getApplicationContext(), "GPS Service Created",
		    Toast.LENGTH_SHORT).show();
		    Log.e("Save my soul", "Service Created");
	}


	@Override
	public void onDestroy() {
	    // TODO Auto-generated method stub
	    super.onDestroy();
	    locationmanager.removeUpdates(this);
	    wakelock.release();
	}

	public static boolean isConnectingToInternet(Context _context) {
	    ConnectivityManager connectivity = (ConnectivityManager) _context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (connectivity != null) {
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();
	        if (info != null)
	            for (int i = 0; i < info.length; i++)
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	                    return true;
	                }

	    }
	    return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		isGPSEnabled = locationmanager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationmanager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (!isGPSEnabled && !isNetworkEnabled) {} 
		else {
			if (isNetworkEnabled) {
				locationmanager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER,
						MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				
				if (locationmanager != null) {
					location = locationmanager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					//Toast.makeText(getApplicationContext(),Double.toString(location.getLatitude())+Double.toString(location.getLongitude()),Toast.LENGTH_SHORT).show();
					latitude = Double.toString(location.getLatitude());
					longitude = Double.toString(location.getLongitude());
					}
			}
			if (isGPSEnabled) {
				if (location == null) {
					locationmanager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationmanager != null) {
						location = locationmanager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						Toast.makeText(getApplicationContext(),Double.toString(location.getLatitude())+Double.toString(location.getLongitude()),Toast.LENGTH_SHORT).show();

						
					}
				}
			}
		}
	 
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
	


