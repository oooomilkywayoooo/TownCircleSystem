package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Admin;
import com.example.app.service.AdminService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	private static final String ADMIN_LOGIN_ERROR = "error.incorrect_id_password";
	private static final String MEMBER_LOGIN_ERROR = "error.incorrect_email_password";

	@Autowired
	AdminService adminService;

	@GetMapping("/admin/login")
	public String adminLoginGet(@RequestParam(name = "status", required = false) String status,
			Model model) throws Exception {
		model.addAttribute("admin", new Admin());
		model.addAttribute("statusMessage", getStatusMessage(status));
		return "admin/adminLogin";
	}

	@PostMapping("/admin/login")
	public String adminLoginPost(@Valid Admin admin, Errors errors, HttpSession session) throws Exception {
		// 入力に不備
		if (errors.hasErrors()) {
			return "admin/adminLogin";
		}

		// パスワードが正しくない
		if (!adminService.isCorrectIdAndPassword(admin.getLoginId(), admin.getLoginPass())) {
			errors.rejectValue("loginId", ADMIN_LOGIN_ERROR);
			return "admin/adminLogin";
		}

		// 正しいログインIDとパスワード
		// セッションにログインIDと管理者名を格納し、リダイレクト
		session.setAttribute("adminLoginId", admin.getLoginId());
		admin = adminService.getAdminByLoginId(admin.getLoginId());
		return "redirect:/admin/home";
	}
	
	@GetMapping("admin/logout")
	public String adminLogout(HttpSession session) {
		// セッションを破棄し、ログインページへ遷移
		session.invalidate();
		return "redirect:/admin/login?status=logout";
	}


	// ログアウトメッセージを生成するメソッド
	private String getStatusMessage(String status) {
		String message = null;
		if (status == null) {
			return message;
		}

		switch (status) {
		case "logout":
			message = "ログアウトしました。";
			break;
		}
		return message;
	}

}
