package com.example.falldetect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.widget.Toast;

//new Http_CLient().execute(String);	
public class Http_client extends AsyncTask<String, Integer, Double>{

	public Http_client(){}
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			postData(params[0],params[1],GPS_coordinateService.latitude,GPS_coordinateService.longitude);
			return null;
		}

		protected void onPostExecute(Double result){
			
			
		}
		protected void onProgressUpdate(final Integer... progress){
		}

		public void postData(String a,String b,String c,String d) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://www.cise.ufl.edu/~rrohit/cps/index.php");

			try {
				// Add your data
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("id",a));
				nameValuePairs.add(new BasicNameValuePair("lat", c));
				nameValuePairs.add(new BasicNameValuePair("long", d));
				nameValuePairs.add(new BasicNameValuePair("flag", b));
				
				

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				final HttpResponse response = httpclient.execute(httppost);
				String responseStr = EntityUtils.toString(response.getEntity());

	            int statusCode = response.getStatusLine().getStatusCode();
	            responseStr = responseStr.trim();
	            String[] numbers = responseStr.split("\\s+");
	            if(FallDetectionService.fallDetect)
	            {
	            	 for(int i=1;i<numbers.length;i++)
	     				{
	     			          sendLocationSMS("+"+numbers[i],GPS_coordinateService.latitude,GPS_coordinateService.longitude);
	     				}
	     				
	            }
	           
				
	            	 else
	            	 {
	     	            FallDetectionService.fallDetect=false;
	     	           for(int i=0;i<numbers.length;i++)
	     				{
	     			          numbers[i]="";
	     				}
	            	 }
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		
		public void sendLocationSMS(String phoneNumber, String lat, String ll) {
		    SmsManager smsManager = SmsManager.getDefault();
		    StringBuffer smsBody = new StringBuffer();
		    String temp = "http://maps.google.com/maps?saddr="+lat+","+ll;
		    Uri myUri = Uri.parse(temp);
		    smsBody.append(myUri); 
		    //smsBody.append(lat);
		    smsManager.sendTextMessage(phoneNumber, null, smsBody.toString(), null, null);
		}


	}

