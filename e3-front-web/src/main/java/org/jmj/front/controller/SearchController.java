package org.jmj.front.controller;

import org.jmj.bean.TbItem;
import org.jmj.bean.TbItemDesc;
import org.jmj.easy.SearchItem;
import org.jmj.easy.SearchResult;
import org.jmj.search.service.SearchService;
import org.jmj.service.ItemSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private ItemSerivce itemSerivce;
	
	/**
	 * 关键词搜索商品
	 * @param keyword
	 * @param model
	 * @return
	 */
	@RequestMapping("/search.html")
	public String serchItemByKeyword(String keyword,Model model) {
		SearchResult result = null;
		try {
			result = searchService.getSearchResult(keyword, 1);
			model.addAttribute("itemResult", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}
	
	/**
	 * 展示商品详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/{id}.html")
	public String itemShow(@PathVariable("id")Long id,Model model){
		TbItem itemById = itemSerivce.getItemById(id);
		SearchItem si = new SearchItem();
		si.setId(itemById.getId());
		String[] images = itemById.getImage().split(",");
		si.setImages(images);
		si.setPrice(itemById.getPrice());
		si.setSellPoint(itemById.getSellPoint());
		si.setTitle(itemById.getTitle());
		model.addAttribute("item", si);
		TbItemDesc desc = itemSerivce.getItemDescById(id);
		model.addAttribute("itemDesc", desc.getItemDesc());
		return "item";
	}
	
}
