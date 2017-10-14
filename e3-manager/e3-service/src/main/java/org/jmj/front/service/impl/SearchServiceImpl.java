package org.jmj.front.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.jmj.easy.SearchItem;
import org.jmj.easy.SearchResult;
import org.jmj.search.service.SearchService;
import org.jmj.solr.SearhClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearhClientBuilder builder;
	
	@Override
	public SearchResult getSearchResult(String keyword, int page)  throws Exception{
		SearchResult result = new SearchResult();
		ArrayList<SearchItem> searcItems  = new ArrayList<>();
		SearchItem item = null;
		
		HttpSolrClient solrClient = builder.build();
		SolrQuery query = new SolrQuery();
		query.setQuery("itemkeywords:" + keyword);
		query.setStart((page - 1) * 60);
		query.setRows(page * 60);

		query.set("df", "title");

		// 设置高亮
		query.setHighlight(true);
		// 设置高亮字段
		query.set("hl.fl", "title");
		query.setParam("hl.q", "itemkeywords:" + keyword);
		query.setHighlightSimplePre("<span style='color:green'>");
		query.setHighlightSimplePost("</span>");

		// 返回结果集
		QueryResponse resp = solrClient.query(query);
		
		SolrDocumentList results = resp.getResults();
		int totals = (int) results.getNumFound();
		
		Map<String, Map<String, List<String>>> highlighting = resp.getHighlighting();
		
		String[] images = null;
		for (SolrDocument solrDocument : results) {
			item = new SearchItem();
			item.setId(solrDocument.get("id"));
			item.setPrice(solrDocument.get("price"));
			images = ((String)solrDocument.get("image")).split(",");
			item.setImages(images);
			item.setSellPoint(solrDocument.get("sellPoint"));
			Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
			for (Entry<String, List<String>> entry : map.entrySet()) {
				item.setTitle(entry.getValue().get(0));
				System.out.println(entry.getValue().get(0));
			}
			searcItems.add(item);
		}
		
		result.setQuery(keyword);
		result.setRecourdCount(totals);
		result.setPage(page);
		result.setTotalPages((int)Math.ceil(totals / 60));
		result.setList(searcItems);
		solrClient.close();
		return result;
	}

}
