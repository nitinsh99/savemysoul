package com.example.falldetect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import android.graphics.YuvImage;
import android.os.Environment;
import android.widget.Toast;

public class RecordData {
	
	private float[] accValue;
	private float[] gyValue;
	private long time;
	File myFile;
	FileOutputStream fOut;
	OutputStreamWriter myOutWriter;
	
	public RecordData(){
		try{
			String path=Environment.getExternalStorageDirectory().getPath();
			myFile = new File("/sdcard/mysdfile.txt");
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
			myOutWriter.close();
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
