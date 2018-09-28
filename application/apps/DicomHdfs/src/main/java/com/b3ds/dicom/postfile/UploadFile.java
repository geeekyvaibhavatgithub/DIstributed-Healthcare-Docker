package com.b3ds.dicom.postfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

public class UploadFile {
	private final static Log logger = LogFactory.getLog(UploadFile.class);
	
	private final String NIFI_REST_URL;
	private String FILE_LOCATION;
	private String FILE_NAME;
	private File FILE;
	
	public UploadFile(final String NIFI_REST_URL, final String FILE_LOCATION, final String FILE_NAME)
	{
		this.NIFI_REST_URL = NIFI_REST_URL;
		this.FILE_LOCATION = FILE_LOCATION;
		this.FILE_NAME = FILE_NAME;
	}
	
	public UploadFile(final File file, final String NIFI_REST_URL)
	{
		this.NIFI_REST_URL = NIFI_REST_URL;
		this.FILE = file;
	}
	
	public String postFile()
	{
		File inFile = FILE;
		logger.info(inFile);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inFile);
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());				
			HttpPut httput = new HttpPut(NIFI_REST_URL);
//			HttpPost httppost = new HttpPost(NIFI_REST_URL);
			
			HttpEntity en = new FileEntity(inFile);
//			httppost.setEntity(en);
			httput.setEntity(en);
			HttpResponse response = httpclient.execute(httput);
			
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity, "UTF-8");
			
			logger.info("[" + statusCode + "] " + responseString);
			return responseString;
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
		
		return Integer.toString(HttpStatus.SC_EXPECTATION_FAILED);
	}
}
