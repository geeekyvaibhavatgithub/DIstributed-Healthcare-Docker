package org.b3ds.dicom.viewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.b3ds.dicom.lucene.LuceneQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class PatientModule extends AbstractPatient{
	
	private final Logger logger = LogManager.getLogger(PatientModule.class);
	private SolrDocumentList list;
	private  String url;
	
	public PatientModule(){
		url = getLuceneUrl();
		logger.debug(url);
	}

	private String getLuceneUrl()
	{
		Properties prop = new Properties();
		String url = null;
		InputStream in = null;
		try {
			in = new FileInputStream(new File("../config.properties"));
			prop.load(in);
			String host = prop.get("solr.host").toString();
			String port = prop.getProperty("solr.port").toString();
			
			url = "http://"+host+":"+port+"/solr";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}
	@Override
	public String getAllPatients() {
		Map<String, String> configuration =  null;
		String url = getLuceneUrl();

		List<String> fields = new ArrayList<String>();
		fields.add("PatientName");
		
		LuceneQuery query = new LuceneQuery(url, "Dicom");
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
		List<Map<String, String>> plist = new ArrayList<>();
		Iterator<String> itr = set.iterator();
		while(itr.hasNext())
		{
			Map<String, String> map = new HashMap<>();
			map.put("PatientName", itr.next());
			plist.add(map);
		}

		return convertToJsonString(plist).replace("\\\"", "");
	}	
	
	
	@Override
	public List<Map<?, ?>> getSinglePatientDetails(String paramName, String paramValue, List<String> fields)
	{
		String url = getLuceneUrl();
		LuceneQuery query = new LuceneQuery(url, "Dicom");
		try {
			list = query.getJson(fields, paramName+":"+paramValue);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		
		Map<String, List<String>> pdetails = mergeJson(list);

		List<Map<?, ?>> plist = Arrays.asList(pdetails);
		return plist;
	}
	
	private String convertToJsonString(SolrDocumentList list)
	{
		Gson gson = new Gson();
		Type type = new TypeToken<SolrDocumentList>(){}.getType();
		String json = gson.toJson(list,type);
		return json;
	}
	
	private String convertToJsonString(List<Map<String, String>> list)
	{
		Gson gson = new Gson();
		Type type = new TypeToken<SolrDocumentList>(){}.getType();
		String json = gson.toJson(list,type);
		return json;
	}
	
	public Map<String, List<String>> mergeJson(SolrDocumentList slist)
	{
		
		Map<String, List<String>> flist = new HashMap<String,List<String>>();		
		for(SolrDocument json : list)
		{
			Set<String> keys = json.keySet();
			Iterator<String> itr = keys.iterator();
			while(itr.hasNext())
			{
				String key = itr.next();
				Object value = json.get(key);
				if((value instanceof List)!=true)
				{
					json.put(key, Arrays.asList(value));
				}
			}
			((Map<String, Object>)json).forEach((k,v) -> flist.merge(k, (List<String>) v,(v1,v2) -> {
				Set<String> set = new HashSet<>(v1);
				set.addAll(v2);
				return new ArrayList<>(set);
			}
			));
		}
		
		return flist;
	}
}
