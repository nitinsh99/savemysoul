package com.example.falldetect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.Toast;

public class FallDetectionService extends Service implements SensorEventListener {
	
	private Sensor accelerometer;
	private Sensor gyroscope;
	private SensorManager manager;
	
	private ArrayList<double []> lastaccValue;
	private ArrayList<double []> lastgyValue;
	
	private final ArrayList<double []> newaccValue=new ArrayList<double[]>();
	private final ArrayList<double []> newgyValue=new ArrayList<double[]>();

	private final int axis=2;
	private long updateTime;
	
	private WakeLock lock; //means that app needs to stay on the device
	private boolean status=false;
	
	private static final float alpha=0.8f;
	private static final double[] gravity= new double[3];
	private static final String TAG = FallDetectionService.class.getSimpleName();;
	
	RecordData handle;

	
	

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		
		if(System.currentTimeMillis() - this.updateTime>=1000){
			long time= System.currentTimeMillis();
			this.lastaccValue= new ArrayList<double []>(this.newaccValue);
			this.lastgyValue=new ArrayList<double[]>(this.newgyValue);
			this.updateTime=time;
			//perform analysis part should be here
			if (this.analyzeFall()) {
				try {
					handle.appData("Yes fall detected"+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

				this.newaccValue.clear();
				this.newgyValue.clear();
			}
		
		else{
			
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				
				double[] values= new double[3];
				values=this.changeValues(event.values);
				double x=values[0];
				double y=values[1];
				double z=values[2];
				String s="Accelerometer:-  " +x+"   "+y+"   "+z+"\n";
				MainActivity.text1.setText(s);
				this.newaccValue.add(values);
				try {
					handle.appData(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
				double[] values= new double[3];
				for(int i=0;i<event.values.length;i++){
					values[i]=event.values[i];
					
				}
				this.newgyValue.add(values);
				double x=values[0];
				double y=values[1];
				double z=values[2];
				String s= "Gyroscope:-  "+x+"   "+y+"   "+z+"\n";
				MainActivity.text2.setText(s);
				try {
					handle.appData(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		}
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		//Wakelock acquire
		PowerManager mgr=(PowerManager)this.getSystemService(Context.POWER_SERVICE);
		this.lock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getName());
		this.lock.acquire();
		
		
		//Sensor initialization
		SensorManager manager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
		
		this.accelerometer= manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.gyroscope=manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		
		this.updateTime = System.currentTimeMillis();
		Log.i(TAG, "Service creating");
		MainActivity.text1.setText("File got a call");
		Toast.makeText(this, "File Got a call", Toast.LENGTH_LONG).show();
		handle=new RecordData();
		this.StartSensors();
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Toast.makeText(this, "FallDetection Service Destroyed", Toast.LENGTH_LONG).show();
		this.lock.release();
		this.stopSensors();
		handle.closeFile();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "FallDetection Service Started", Toast.LENGTH_LONG).show();
		return START_STICKY;
	}
	
	
	public void StartSensors(){
		System.out.println("Starting snsors");
		manager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
		manager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(this, this.gyroscope, SensorManager.SENSOR_DELAY_NORMAL);

		this.status = true;
	}
	
	public void stopSensors(){
		
		System.out.println("Stoping sensors");
		//manager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
		manager.unregisterListener(this);
		this.status = false;
	}
	
	
	private double[] changeValues(float[] values){ //fillter for accelerometer
		
		 double[] newValues=new double[3];
		
		gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0];
		gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1];
		gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2];
	
		newValues[0]= values[0]-gravity[0];
		newValues[1]= values[1]-gravity[1];
		newValues[2]= values[2]-gravity[2];
		return newValues;
		
	}
	

			private boolean analyzeFall() {
			
			if (this.lastgyValue == null)
				return false;

			boolean accel = this.analyzeAcceleration();
			boolean ang = this.analyzeAngularVelocity();
			boolean angVar = this.analyzeAngularVariation();

			return accel && ang && angVar;

		}

			private boolean analyzeAcceleration() {
			double maxHori = Double.MIN_VALUE;
			double maxMovement = Double.MIN_VALUE;

			for (double[] accelValues : this.newaccValue) {
				System.out.println(Arrays.toString(accelValues));
				if (Math.abs(accelValues[this.axis]) > maxMovement)
					maxMovement = Math.abs(accelValues[this.axis]);

				if (Math.abs(accelValues[0]) > maxHori)
					maxHori = Math.abs(accelValues[0]);

			}

			double value = -0.139 + 0.0195 * maxHori + 0.0163 * maxMovement;
			return value >= 0.05;
		}

			private boolean analyzeAngularVelocity() {
				double maxValue = Double.MIN_VALUE;
				
				for (double[] accelValues : this.newgyValue) {
						for (double value : accelValues) {
							if (Math.abs(value) > maxValue)
								maxValue = Math.abs(value);

						}

				}
				
				return maxValue > 2.4543;

			}

			private boolean analyzeAngularVariation() {
				int n = this.lastgyValue.size() + this.newgyValue.size();
				double[] x = new double[n];

				for (int i = 0; i < x.length; i++) {
					x[i] = i;

				}

				double[] y = new double[n];
				int c = 0;
				while(c < this.lastgyValue.size()) {
					double[] gValues = this.lastgyValue.get(c);
					y[c] = Math.abs(gValues[0]);
					c++;

				}

				while (c < this.newgyValue.size()) {
					double[] gValues = this.newgyValue.get(c);
					y[c] = Math.abs(gValues[0]);
					c++;

				}

				double value = TIntegration.integrate(x, y);
				System.out.println("" + value);

				return value > 0.872664626;

			}


	
	
	
}
