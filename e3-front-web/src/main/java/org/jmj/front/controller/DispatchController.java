package org.jmj.front.controller;

import org.jmj.front.service.FrontContcentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DispatchController {
	
	@Autowired
	private FrontContcentCategoryService frontContcentCategoryService;
	
	@RequestMapping("/index.html")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("index");
		view.addObject("ad1List", frontContcentCategoryService.getConcont(89L));
		return view;
		
	}

}
