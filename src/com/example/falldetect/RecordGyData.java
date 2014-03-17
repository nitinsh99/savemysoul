package com.example.falldetect;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import android.graphics.YuvImage;
import android.os.Environment;
import android.widget.Toast;

public class RecordGyData {
	

	private long time;
	File myFile;
	FileOutputStream fOut;
	OutputStreamWriter myOutWriter;
	
	public RecordGyData(){
		try{
			String path=Environment.getExternalStorageDirectory().getPath();
			myFile = new File("/sdcard/Gyroscope.txt");
			myFile.createNewFile();
			fOut = new FileOutputStream(myFile);
			myOutWriter = new OutputStreamWriter(fOut);
			String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
			myOutWriter.append(currentDateTimeString+"    "+"Data collection start"+"\n\n");
			MainActivity.text1.setText("File has been created");
		}
			catch(Exception e){}
		
	}
	
	public void appData(String s) throws IOException{
		myOutWriter.append(s);
	}
	
	public void closeFile(){
		try {
			String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());			
			myOutWriter.append(currentDateTimeString+"    "+"Data collection ends"+"\n\n");
			myOutWriter.close();
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
