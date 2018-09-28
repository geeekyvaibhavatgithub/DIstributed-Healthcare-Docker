package com.b3ds.dicom.postfile.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import com.b3ds.dicom.postfile.UploadFile;

public class DicomUpdateTest {
	
	public void mains(String[] args) throws IOException
	{
			File inFile = new File("C:\\Users\\Zero.DESKTOP-UK4J6CU\\Desktop\\storage\\demo.dcm");
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(inFile);
				DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());				
				HttpPost httppost = new HttpPost("http://192.168.1.16:7001/dicom");
				
				HttpEntity en = new FileEntity(inFile);
				httppost.setEntity(en);
				
				HttpResponse response = httpclient.execute(httppost);
				
				int statusCode = response.getStatusLine().getStatusCode();
				HttpEntity responseEntity = response.getEntity();
				String responseString = EntityUtils.toString(responseEntity, "UTF-8");
				
				System.out.println("[" + statusCode + "] " + responseString);
				
			} catch (ClientProtocolException e) {
				System.err.println("Unable to make connection");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Unable to read file");
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) fis.close();
				} catch (IOException e) {}
			}
		}	
	
	public static void main(String[] args)
	{
		UploadFile file = new UploadFile(new File("C:\\Users\\Zero.DESKTOP-UK4J6CU\\Desktop\\storage\\demo.dcm"), "http://192.168.1.16:7001/dicom");
		file.postFile();
	}
}
