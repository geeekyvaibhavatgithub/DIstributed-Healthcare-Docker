package com.b3ds.dicom.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

public class LuceneQuery {
	
	private final String BASE_URL;
	private final String COLLECTION_NAME;

	private final static Log logger = LogFactory.getLog(LuceneQuery.class);
	
	public LuceneQuery(final String BASE_URL, final String COLLECTION_NAME)
	{
		this.BASE_URL = BASE_URL;
		this.COLLECTION_NAME = COLLECTION_NAME;
	}
	
	public SolrDocumentList getJson(List<String> fields, String queryString) throws SolrServerException, IOException
	{
		logger.debug("preparing lucene query");
		SolrClient solr = LucenSolrClient.getClient(BASE_URL);
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", queryString);
		map.put("fl", "");
		Iterator<String> itr = fields.iterator();
		int i = 1;
		while(itr.hasNext())
		{
			if(i==fields.size())
			{
				map.put("fl", map.get("fl").toString().concat(itr.next()));
			}
			else
			{
				map.put("fl", map.get("fl").toString().concat(itr.next()).concat(","));
			}
			i++;
		}
		MapSolrParams params = new MapSolrParams(map);
		final QueryResponse response = solr.query(COLLECTION_NAME, params);
		for(SolrDocument doc : response.getResults())
		{
			format(doc);
		}
		solr.close();
		return response.getResults();
	}
	
	public SolrDocumentList getJson(List<String> filter, List<String> fields, String queryString) throws SolrServerException, IOException
	{
		logger.debug("preparing lucene query");
		SolrClient solr = LucenSolrClient.getClient(BASE_URL);
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", queryString);
		map.put("fl", "");
		map.put("fq", "");
		
		Iterator<String> itr = fields.iterator();
		int i = 1;
		while(itr.hasNext())
		{
			if(i==fields.size())
			{
				map.put("fl", map.get("fl").toString().concat(itr.next()));
			}
			else
			{
				map.put("fl", map.get("fl").toString().concat(itr.next()).concat(","));
			}
			i++;
		}
		
		Iterator<String> filterItr = filter.iterator();
		int j = 1;
		
		while(filterItr.hasNext())
		{
			if(i==filter.size())
			{
				map.put("fq", map.get("fq").toString().concat(filterItr.next()));
			}
			else
			{
				map.put("fq", map.get("fq").toString().concat(filterItr.next()).concat(","));
			}
			j++;
		}

		MapSolrParams params = new MapSolrParams(map);
		final QueryResponse response = solr.query(COLLECTION_NAME, params);
		for(SolrDocument doc : response.getResults())
		{
			format(doc);
		}
		return response.getResults();
	}

	private SolrDocument format(SolrDocument doc)
	{
			Set<String> keyset = doc.keySet();
			for(String key : keyset)
			{
				doc.put(key, doc.get(key).toString().replace("[", "").replace("]", ""));
			}
			return doc;
	}
		
}
