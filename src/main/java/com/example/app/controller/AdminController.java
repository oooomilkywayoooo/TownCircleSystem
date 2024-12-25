package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/home")
	public String home(@RequestParam(name="status", required=false) String status, Model model) {
		return "admin/adminHome";
	}
}
