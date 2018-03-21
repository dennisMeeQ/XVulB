package com.mgmtp.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mgmtp.blog.model.PostDTO;
import com.mgmtp.blog.service.PostService;
import com.mgmtp.blog.setting.SecuritySettings;

@Controller
public class SearchController {
	
	@Autowired
	PostService postService;
	@Autowired
	SecuritySettings securitySettings;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String showSearchResult(Model model, HttpServletRequest request, HttpServletResponse response) {
		String query = request.getParameter("query");
		if(query.length() == 0) return "redirect:/";
		
		List<PostDTO> posts;
		switch (securitySettings.getSqlInjection()) {
			case True:
				posts = (List<PostDTO>) postService.findByTitle(query,true);				
				break;
			default:
				posts = (List<PostDTO>) postService.findByTitle(query,false);	
				
		}
    
	    	model.addAttribute("searchquery", query);
		model.addAttribute("posts", posts);
	    return "search";
	}
}