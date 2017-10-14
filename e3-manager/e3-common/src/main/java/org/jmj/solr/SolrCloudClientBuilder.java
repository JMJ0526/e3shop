package org.jmj.solr;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient.Builder;

/**
 * 描述: solr 集群版客户端
 * @author jmj
 */
public class SolrCloudClientBuilder {
	
	private String solrUrl;
	
	private String zkHosts;

	private static Builder builder;
	
	public void init() {
		builder = new Builder();
	}
	
	public CloudSolrClient build() {
		return builder.withZkHost(zkHosts).withSolrUrl(solrUrl).build();
	}

	public String getZkHosts() {
		return zkHosts;
	}

	public void setZkHosts(String zkHosts) {
		this.zkHosts = zkHosts;
	}

	public String getSolrUrl() {
		return solrUrl;
	}

	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}
	
}
