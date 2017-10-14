package org.jmj.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;

/**
 * 描述: solr单机版
 * @author jmj
 */
public class SearhClientBuilder {
	
	private String solrUrl;
	
	private static Builder builder;
	
	public void init() {
		builder = new Builder(solrUrl);
	}
	
	public HttpSolrClient build() {
		return builder.build();
	}
	
	public String getSolrUrl() {
		return solrUrl;
	}

	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}
	

}
