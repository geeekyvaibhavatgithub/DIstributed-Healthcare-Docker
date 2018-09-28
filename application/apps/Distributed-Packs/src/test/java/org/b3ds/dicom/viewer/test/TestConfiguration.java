package org.b3ds.dicom.viewer.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.b3ds.dicom.viewer.PatientModule;
import org.junit.Test;


public class TestConfiguration {
	private final static Logger logger = LogManager.getLogger(TestConfiguration.class);
		
	@Test
	public void TestPatientModule()
	{
		logger.debug("Configuration test");
		PatientModule module = new PatientModule();
	}
	
	public void createPropertiesFile()
	{
		System.out.println(System.getProperty("user.dir"));
		String propFile = "E:\\WebserviceTutorial\\SpringRest\\config.properties";
		Properties prop = new Properties();
		OutputStream output = null;
		
		try{
			output = new FileOutputStream(new File(propFile));
			prop.setProperty("solr.host", "localhost");
			prop.setProperty("solr.port", "1000");
			prop.setProperty("nifi.host", "localhost");
			prop.setProperty("nifi.host", "8080");
			prop.setProperty("kafka.rest.host", "localhost");
			prop.setProperty("kafka.rest.port", "1111");
			prop.setProperty("mongo.host", "localhost");
			prop.setProperty("mongo.port", "27017");
			prop.setProperty("mysql.host", "localhos");
			prop.setProperty("mysql.port", "3306");
			prop.setProperty("packs.port", "5000");
			prop.store(output, "Hello");
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally{
			if(output != null)
			{
				try{
					output.close();
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
