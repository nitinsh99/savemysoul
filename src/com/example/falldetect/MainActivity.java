package com.example.falldetect;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button button;//start button
	Button button2;//Stop Button
	static EditText text1;
	static EditText text2;
	
	public static String latitude;
	public static String longitude;
	

	String androidId;
	String deviseId = null;
	private Handler mHandler;
	String line = null;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.linerlayout);

		button = (Button) findViewById(R.id.button1);
		button2=(Button) findViewById(R.id.button2);
		text1=(EditText)findViewById(R.id.editText1);
		text2=(EditText)findViewById(R.id.editText2);
		 //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			//StrictMode.setThreadPolicy(policy); 
		button.setOnClickListener(new OnClickListener() {	 
			@Override
			public void onClick(View arg0) {
				startMyService();
				 System.out.println("asa");
				 //text.setText("Service Started");
				 
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopMyService();
			
			}
		});
		
		 String ts = Context.TELEPHONY_SERVICE;
       	 TelephonyManager tm = (TelephonyManager) getSystemService(ts);
            deviseId = tm.getDeviceId().toString();
            
          //  new client().execute("");


         //   new Server().execute("");


            
		
		}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void startMyService(){
		Intent intent = new Intent(this, FallDetectionService.class);
		this.startService(intent);
		Intent intent1 = new Intent(this, GPS_coordinateService.class);
		this.startService(intent1);
		
		

		
	}
	
	private void stopMyService(){
		Intent intent = new Intent(this, FallDetectionService.class);
		this.stopService(intent);
		Intent intent1 = new Intent(this, GPS_coordinateService.class);
		this.stopService(intent1);
		
	}
	
	

	
	/*
	private class client extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

   		 
        	
   						LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
   						LocationListener locationListener = new LocationListener() {
   						    public void onLocationChanged(Location location) {


   						    	latitude = Double.toString(location.getLatitude());
   						    	longitude = Double.toString(location.getLongitude());
   						    	
   						     MainActivity.this.runOnUiThread(new Runnable() {

   					            @Override
   					            public void run() {
   							    	Toast.makeText(getApplicationContext(), ""+latitude+longitude+deviseId, Toast.LENGTH_SHORT).show();

   					            }
   					        });
   						    	
   						    	new post().execute("");


   						    }

   						    public void onStatusChanged(String provider, int status, Bundle extras) {}

   						    public void onProviderEnabled(String provider) {}

   						    public void onProviderDisabled(String provider) {}
   						  };

   						//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
   					
            
            return null;
        }
	}

	private class Server extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
        	

   						try {
   							

   							
   			                if (deviseId != null) {
   			                	
   			                   ServerSocket serverSocket = new ServerSocket(1979);
   			                    while (true) {
   			                        // listen for incoming clients

      			                    
   			                    	Socket server = serverSocket.accept();
   			                     
    			                    
   							    	//Toast.makeText(getApplicationContext(),"accepting connection", Toast.LENGTH_SHORT).show();

   			                        try {
   			                            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
   			                            while ((line = in.readLine()) != null) {
   			                                Log.d("ServerAmctivity", line);
   			                                mHandler.post(new Runnable() {
   			                                    @Override
   			                                    public void run() {
   			                                        // do whatever you want to the front end
   			                                        // this is where you can be creative
   											    	Toast.makeText(getApplicationContext(),line, Toast.LENGTH_SHORT).show();
   			                                    }
   			                                });
   			                            }
   			                            break;
   			                        } catch (Exception e) {
   			                            mHandler.post(new Runnable() {
   			                                @Override
   			                                public void run() {
   										    	Toast.makeText(getApplicationContext(),"Conneckstion interrupted", Toast.LENGTH_SHORT).show();

   			                                }
   			                            });
   			                            e.printStackTrace();
   			                        }
   			                    }
   			                } else {
   			                    mHandler.post(new Runnable() {
   			                        @Override
   			                        public void run() {
   								    	Toast.makeText(getApplicationContext(),"Connectioln interrupted", Toast.LENGTH_SHORT).show();

   			                            //text2.setText("Couldn't detect internet connection.");
   			                        }
   			                    });
   			                }
   			            } catch (Exception e) {
   			                mHandler.post(new Runnable() {
   			                    @Override
   			                    public void run() {
   							    	Toast.makeText(getApplicationContext(),"Ersror", Toast.LENGTH_SHORT).show();
   			                    }
   			                });
   			                e.printStackTrace();
   			            }
            return null;
        }
	}
	
	
	
	
	private class post extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
        	// Create a new HttpClient and Post Header
    	    HttpClient httpclient = new DefaultHttpClient();
    	    HttpPost httppost = new HttpPost("http://ikwc.t.proxylocal.com/savemysoul/index.php");

    	    try {
    	        // Add your data
    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	        nameValuePairs.add(new BasicNameValuePair("id", deviseId));
    	        nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
    	        nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
    	        nameValuePairs.add(new BasicNameValuePair("ip", "null"));
    	        nameValuePairs.add(new BasicNameValuePair("longitude", "0"));
    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    	        // Execute HTTP Post Request
    	        HttpResponse httpresponse = httpclient.execute(httppost);
    	        StatusLine statusLine = httpresponse.getStatusLine();
    	        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
    	        ByteArrayOutputStream out = new ByteArrayOutputStream();
    	        httpresponse.getEntity().writeTo(out);
    	        out.close();
    	        final String responseString = out.toString();
    	        
    	        


    	      
    	        }
    	        else
    	        {
    	        	
    	        	
    	        }
    	        
    	        
    	    } catch (ClientProtocolException e) {
    	        // TODO Auto-generated catch block
    	    } catch (IOException e) {
    	        // TODO Auto-generated catch block
    	    }
   		 
            
            return null;
        }
	}
	
	*/

}
