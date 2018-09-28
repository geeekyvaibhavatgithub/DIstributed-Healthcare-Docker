package com.b3ds.dicom.hdfs;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HDFSUrl {
	private static final Log logger = LogFactory.getLog(HDFSUrl.class);
	
	private String url = null;
	private String host = null;
	private String port = null;
	private String path = null;
	private String fileName = null;
	private String userName = null;
	private String operation = null;
	private URI uri = null;
	
	private final String BASE = "/webhdfs/v1";
	
	public HDFSUrl(){
	}
	
	public HDFSUrl(String url, String username, String operation, String path) throws URISyntaxException
	{
		this.userName = username;
		this.url = url;
		this.path = path;
		this.operation = operation;
		this.uri = new URI(url+BASE+path+"?user.name="+username+"&op="+operation);
	}
	
	public HDFSUrl(String url, String userName,String operation, String path, String fileName) throws URISyntaxException
	{
		this.userName = userName;
		this.path = path;
		this.fileName = fileName;
		System.out.println(path);
		this.uri = new URI("http://"+url+BASE+path+"/"+fileName+"?user.name="+userName+"&op="+operation);
	}
	
	public HDFSUrl(String host, String port, String userName,String operation, String path, String fileName) throws URISyntaxException
	{
		this.host = host;
		this.port = port;
		this.path = path;
		this.fileName = fileName;
		this.userName = userName;
		this.operation = operation;
		this.uri = new URI("http://"+host+":"+port+BASE+path+"/"+fileName+"?user.name="+userName+"&op="+operation);
	}
	
	public URI getUri()
	{
		return this.uri;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}

