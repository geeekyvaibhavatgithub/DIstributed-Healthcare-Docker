package com.b3ds.dicom.hdfs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import com.b3ds.dicom.hdfs.GetDicom;
import com.b3ds.dicom.hdfs.HDFSUrl;

public class TestHDFS {
	private static final Log logger =  LogFactory.getLog(TestHDFS.class);
	private String url = "192.168.1.16:50070";
	private String username = "b3ds";
	private String operation = "OPEN";
	private String path = "storage/FELIX";
	private String filename = "IM-0001-0074.dcmfe2f32c2-0564-45a2-9aa7-6c71c6a6913d.dcm";
	private String location = "C:\\Users\\Zero.DESKTOP-UK4J6CU\\Desktop\\";

	public void testwritefile() throws IOException, URISyntaxException
	{
		GetDicom dicom = new GetDicom();
		dicom.writeLocalFile(url,username,operation,path,filename,location);
	}
	
	public void getFileName() throws URISyntaxException, IOException
	{
		HDFSUrl hdfsUri = new HDFSUrl("http://192.168.1.16:50070", "b3ds", "OPEN", "storage/FELIX/IM-0001-0074.dcmfe2f32c2-0564-45a2-9aa7-6c71c6a6913d.dcm");
		URI uri =  hdfsUri.getUri();
		URL url = uri.toURL();
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		System.out.println(conn.getContent());
	}
	
	public static void main(String args[]) throws URISyntaxException, IOException
	{
		String url = "http://192.168.1.16:50070/webhdfs/v1/ss?op=CREATE&overwrite=true&user.name=b3ds";
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut httput = new HttpPut(url);
				
		HttpResponse response = client.execute(httput);
		
		int statusCode = response.getStatusLine().getStatusCode();
		HttpEntity responseEntity = response.getEntity();
		String responseString = EntityUtils.toString(responseEntity, "UTF-8");
		
		System.out.println(statusCode);
		System.out.println(responseEntity);
		
		File file = new File("F:\\B3DSProjects\\DicomHdfs\\demo.txt");
		String urls = "http://192.168.1.16:50075/webhdfs/v1/ss/demo.txt?op=CREATE&namenoderpcaddress=0.0.0.0:8020&overwrite=true&user.name=b3ds";
		putfile(urls, file);
	}
	
	public static void putfile(String url, File FILE)
	{
		File inFile = FILE;
		logger.info(inFile);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inFile);
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());				
			HttpPut httput = new HttpPut(url);
			httput.setHeader("Content-Type","application/octet-stream");
			HttpEntity en = new FileEntity(inFile);
			httput.setEntity(en);
			HttpResponse response = httpclient.execute(httput);
			
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity, "UTF-8");
			
			logger.info("[" + statusCode + "] " + responseString);
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
}