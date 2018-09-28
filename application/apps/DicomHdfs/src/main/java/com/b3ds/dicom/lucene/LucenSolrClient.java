package com.b3ds.dicom.lucene;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public final class LucenSolrClient {
	
	private static final Log logger = LogFactory.getLog(LucenSolrClient.class);
	
	public static SolrClient getClient(final String BASE_URL)
	{
		logger.info("Initializing Solr client..");
		return new HttpSolrClient.Builder(BASE_URL).build();
	}
}
