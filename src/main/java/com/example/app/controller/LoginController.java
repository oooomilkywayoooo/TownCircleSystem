package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Admin;
import com.example.app.domain.Member;
import com.example.app.domain.MemberForm;
import com.example.app.service.AdminService;
import com.example.app.service.GroupService;
import com.example.app.service.MemberService;
import com.example.app.validation.LoginMember;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	private static final String ADMIN_LOGIN_ERROR = "error.incorrect_id_password";
	private static final String MEMBER_LOGIN_ERROR = "error.incorrect_email_password";

	@Autowired
	AdminService adminService;
	@Autowired
	MemberService memberService;
	@Autowired
	GroupService groupService;

	/////////////////////
	///管理者ログイン機能///
	/////////////////////
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
		// セッションにログインIDを格納し、リダイレクト
		admin = adminService.getAdminByLoginId(admin.getLoginId());
		session.setAttribute("adminLoginId", admin.getLoginId());
		return "redirect:/admin/home";
	}

	@GetMapping("/admin/logout")
	public String adminLogout(HttpSession session) {
		// セッションを破棄し、ログインページへ遷移
		session.invalidate();
		return "redirect:/admin/login?status=logout";
	}

	///////////////////
	///会員ログイン機能///
	///////////////////
	@GetMapping("/login")
	public String memberLoginGet(Model model) throws Exception {
		model.addAttribute("memberForm", new MemberForm());
		model.addAttribute("groupList", groupService.getGroupList());
		return "member/login";
	}

	@PostMapping("/login")
	public String memberLoginPost(@Validated(LoginMember.class) MemberForm form,
			Errors errors, HttpSession session) throws Exception {
		// 入力に不備
		if (errors.hasErrors()) {
			return "member/login";
		}

		// パスワードが正しくない
		if (!memberService.isCorrectEmailAndPassword(form.getEmail(), form.getPassword())) {
			errors.rejectValue("email", MEMBER_LOGIN_ERROR);
			return "member/login";
		}

		// 正しいEmailとパスワード
		// セッションに会員IDと会員名を格納し、リダイレクト
		Member member = memberService.getMemberByEmail(form.getEmail());
		session.setAttribute("memberId", member.getId());
		session.setAttribute("memberName", member.getName());
		return "redirect:/home";
	}
	
	@GetMapping("/logout")
	public String memberLogout(HttpSession session) {
		// セッションを破棄し、ログアウトページへ遷移
		session.invalidate();
		return "member/logout";
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
