package org.lessons.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	
	@GetMapping("/")
	public String homepage(Model model) {
		return "/pages/homepage";
	}
	
	@GetMapping("pages/login")
    public String login(Model model) {
        return "/pages/login";
    }

   

}
