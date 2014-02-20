package com.example.falldetect;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button button;//start button
	Button button2;//Stop Button
	static EditText text1;
	static EditText text2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linerlayout);
		button = (Button) findViewById(R.id.button1);
		button2=(Button) findViewById(R.id.button2);
		text1=(EditText)findViewById(R.id.editText1);
		text2=(EditText)findViewById(R.id.editText2);
		button.setOnClickListener(new OnClickListener() {	 
			@Override
			public void onClick(View arg0) {
				startMyService();
				 System.out.println("asa");
				 text1.setText("Service Started");
				 
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopMyService();
			
			}
		});
		
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
		
	}
	
	private void stopMyService(){
		Intent intent = new Intent(this, FallDetectionService.class);
		this.stopService(intent);
		
	}
	
	
	

}
