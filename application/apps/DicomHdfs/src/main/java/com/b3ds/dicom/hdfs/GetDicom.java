package com.b3ds.dicom.hdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetDicom {
	private static final Log logger = LogFactory.getLog(GetDicom.class);
	
	private InputStream getFileAsStream(HDFSUrl hdfsUri) throws URISyntaxException, IOException
	{
		URI uri =  hdfsUri.getUri();
		URL url = uri.toURL();
		System.out.println(url);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);
		InputStream in = null;
		if(responseCode == HttpURLConnection.HTTP_OK)
		{
			 System.out.println("print");
			 in = conn.getInputStream();
		}
		return in;
	}
	
	public void writeLocalFile(String url, String username, String operation, String path, String filename, String location) throws IOException, URISyntaxException
	{
		HDFSUrl hdfsUri = new HDFSUrl(url, username, operation, path, filename);
		InputStream input = getFileAsStream(hdfsUri);
		System.out.println(input);
		FileOutputStream out = new FileOutputStream(new File(location+filename));
		IOUtils.copy(input, out);
		out.close();
		input.close();
	}

}
