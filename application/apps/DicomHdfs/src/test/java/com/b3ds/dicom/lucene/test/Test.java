package com.b3ds.dicom.lucene.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;

import com.b3ds.dicom.lucene.LuceneQuery;

public class Test {

	public static void main2(String[] args) throws SolrServerException, IOException {
		
		LuceneQuery q = new LuceneQuery("http://192.168.1.16:8983/solr", "Dicom");
		List<String> list = new ArrayList<String>();
		list.add("FileName");
		list.add("FileLocation");
		
		List<String> filters = new ArrayList<String>();
		
	}

	public static void main(String args[]) throws SolrServerException, IOException
	{
		SolrClient client = new HttpSolrClient.Builder("http://192.168.1.16:8983/solr").build();
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "SeriesInstanceUID:1.3.12.2.1107.5.2.13.20561.30010005042219430807800001431");
		map.put("fl", "FileName, FileLocation");
		System.out.println(map);
/*		MapSolrParams params = new MapSolrParams(map);
		final QueryResponse response = client.query("Dicom", params);
		System.out.println(response.getResponse());*/
		
	}
}
