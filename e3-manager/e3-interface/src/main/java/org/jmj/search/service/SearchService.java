package org.jmj.search.service;

import org.jmj.easy.SearchResult;

public interface SearchService {
	
	
	/**
	 * 获取搜索结果集
	 * @param keyword
	 * @param page
	 * @return
	 * @throws Exception
	 */
	SearchResult getSearchResult(String keyword,int page) throws Exception;
	
}
