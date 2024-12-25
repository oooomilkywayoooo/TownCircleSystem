package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.service.FunctionService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	FunctionService functionService;
	
	@GetMapping("/home")
	public String home(@RequestParam(name="status", required=false) String status, Model model)
				throws Exception{
		model.addAttribute("infoList", functionService.getNotificationList());
		return "admin/adminHome";
	}
}
