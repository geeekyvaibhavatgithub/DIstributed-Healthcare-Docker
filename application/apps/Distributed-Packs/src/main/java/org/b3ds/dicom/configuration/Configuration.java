package org.b3ds.dicom.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;

public class Configuration {
		
	private static final Logger logger = LogManager.getLogger(Configuration.class);
	public final boolean FILE_EXIST;
	
	public Configuration(String path)
	{
		FILE_EXIST = checkFileExist(path);
	}
	
	private static org.jdom2.Document parser(String filename) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.parse(new File(filename));
		DOMBuilder builder2 = new DOMBuilder();
		return builder2.build(doc);
	}
	
	public Map<String, String> getConfigurationDetails(String xmlLocation) throws ParserConfigurationException, SAXException, IOException
	{
		logger.info("Reading configuration from "+ xmlLocation+ " location");
		org.jdom2.Document doc = parser(xmlLocation);
		Element root = doc.getRootElement();

		String solrhost = root.getChild("solr").getChildText("host");
		String solrport = root.getChild("solr").getChildText("port");
		String solrcollection = root.getChild("solr").getChildText("collection");
		Map<String, String> map = new HashMap<String, String>();
		map.put("solrhost", solrhost);
		map.put("solrport", solrport);
		map.put("solrcollection", solrcollection);
		return map;
	}
	
	public void createConfigurationFile(String path)
	{
		logger.info("Creating configuration file");
		
	}
	
	public boolean checkFileExist(String path)
	{
		File file = new File(path);
		if(file.exists())
		{
			return true;
		}
		
		return false;
	}
}
