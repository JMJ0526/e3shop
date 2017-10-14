package org.jmj.front.service;

import java.util.List;

import org.jmj.bean.TbContent;

public interface FrontContcentCategoryService {
	
	/**
	 * 获取index的轮播广告内容
	 * @param cid
	 * @return
	 */
	List<TbContent> getConcont(Long cid);
	
}
