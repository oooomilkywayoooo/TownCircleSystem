package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	
	//////////////
	///ホーム画面///
	//////////////
	@GetMapping("/home")
	public String home(Model model, HttpSession session) {
		// ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		return "member/home";
	}
}
