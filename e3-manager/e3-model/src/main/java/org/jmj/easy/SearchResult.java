package org.jmj.easy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 
 * @author jmj
 */
public class SearchResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SearchItem> list = new ArrayList<>();
	
	private int totalPages;
	
	private int recourdCount;
	
	private Object query;
	
	private int page;

	public List<SearchItem> getList() {
		return list;
	}

	public void setList(List<SearchItem> list) {
		this.list = list;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getRecourdCount() {
		return recourdCount;
	}

	public void setRecourdCount(int recourdCount) {
		this.recourdCount = recourdCount;
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	
}
