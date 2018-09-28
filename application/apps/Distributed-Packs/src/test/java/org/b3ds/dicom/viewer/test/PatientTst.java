package org.b3ds.dicom.viewer.test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.b3ds.dicom.viewer.PatientModule;

import com.b3ds.dicom.lucene.LuceneQuery;

public class PatientTst {
	private final static Logger logger = LogManager.getLogger(PatientTst.class);
	LuceneQuery query = new LuceneQuery("http://192.168.1.16:8983/solr", "Dicom");

	public void testgetSinglePatient()
	{
/*		PatientModule details = new PatientModule();
		List<PatientDetails> de = details.getSinglePatient("FELIX");*/
		 String str = "PatientID";
		System.out.println(WordUtils.uncapitalize(str));
		Integer in = 19220211;
	}
	
	public void getallPatients()
	{
		List<SolrDocument> list = new ArrayList<>();
		Map<String, String> configuration =  null;
/*		try {
			configuration = Configuration.getConfigurationDetails("F:\\B3DSProjects\\DicomViewer\\src\\main\\resources\\Configuration.xml");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Configuration File missing");
		}		*/
		List<String> fields = new ArrayList<String>();
		fields.add("PatientName");
		fields.add("PatientID");
		LuceneQuery query = new LuceneQuery("http://192.168.1.16:8983/solr", "Dicom");
		try {
			list = query.getJson(fields, "*:*");
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashSet<String> set = new HashSet<>();
		for(SolrDocument doc : list)
		{
			set.add((String) doc.get("PatientName"));
		}
		logger.debug(set);
		List<Map<String, String>> plist = new ArrayList<>();
		Iterator<String> itr = set.iterator();
		while(itr.hasNext())
		{
			Map<String, String> map = new HashMap<>();
			map.put("PatientName", itr.next());
			plist.add(map);
		}
		logger.debug(plist);
	}
	
	public void testSinglePatientDetail()
	{
/*		PatientModule module = new PatientModule();
		logger.debug(module.getSinglePatient("FELIX"));
*/	}
	
	public void testSpecialCharacterRemoval()
	{
		String str = "Gastric^Single Isotope";
		str = str.replaceAll("[\\^\\&\\.]", " ");
		logger.debug(str);
	}
	
	public void testGetSeriesDetails()
	{
		List<String> fields = Arrays.asList("SeriesInstanceUID","SeriesDescription","SeriesNumber");
		PatientModule module = new PatientModule();
		logger.debug(module.getSinglePatientDetails("StudyID", "4460172", fields));
	}

	public void testGetSeriesImage()
	{
		Map<String, String> seriesId = new HashMap<>();
		seriesId.put("seriesId", "1.3.12.2.1107.5.2.13.20561.30010005042219430807800001431");
		List<String> fields = Arrays.asList("FileName","FileLocation");
		PatientModule module = new PatientModule();
//		logger.debug(module.getSinglePatientDetails("SeriesInstanceUID", "1.3.12.2.1107.5.2.13.20561.30010005042219430807800001431", fields));
		logger.debug(module.getSinglePatientDetails("SeriesInstanceUID",seriesId.get("seriesId"),fields));		
	}
}

