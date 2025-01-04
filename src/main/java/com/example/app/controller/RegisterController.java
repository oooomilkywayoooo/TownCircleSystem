package com.example.app.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.Member;
import com.example.app.domain.MemberForm;
import com.example.app.service.GroupService;
import com.example.app.service.MemberService;
import com.example.app.validation.EditMember;
import com.example.app.validation.EditPassword;
import com.example.app.validation.RegisterMember;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {
	private static final String NOT_SAME_PASS = "error.incorrect_poss_passConf";
	private static final String ALREADY_REGIST = "error.already_email_regist";

	@Autowired
	MemberService memberService;
	@Autowired
	GroupService groupService;

	////////////////
	///会員登録機能///
	////////////////
	@GetMapping("/register")
	public String addGet(Model model) throws Exception {
		model.addAttribute("memberForm", new MemberForm());
		model.addAttribute("groupList", groupService.getGroupList());
		return "member/signup";
	}

	@PostMapping("/register")
	public String add(@Validated(RegisterMember.class) MemberForm form,
			Errors errors, Model model, HttpSession session) throws Exception {
		// パスワードと確認用パスワードが一致しない場合
		if (!form.getPassword().equals(form.getPasswordConf())) {
			errors.rejectValue("password", NOT_SAME_PASS);
		}
		// 入力に不備
		if (errors.hasErrors()) {
			model.addAttribute("groupList", groupService.getGroupList());
			return "member/signup";
		}

		try {
			memberService.addMember(form);
			Member member = memberService.getMemberByEmail(form.getEmail());
			session.setAttribute("memberId", member.getId());
			session.setAttribute("memberName", member.getName());
			return "redirect:/home?status=add";
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
				errors.rejectValue("email", ALREADY_REGIST);
			}
			model.addAttribute("groupList", groupService.getGroupList());
			return "member/signup";
		}
	}

	////////////////
	///会員編集機能///
	////////////////
	@GetMapping("/edit/{id}")
	public String editGet(@PathVariable Integer id, Model model, HttpSession session) throws Exception {
		Member member = memberService.getMemberById(id);
		model.addAttribute("member", member);
		model.addAttribute("groupList", groupService.getGroupList());
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		return "member/memberEdit";
	}

	@PostMapping("/edit/{id}")
	public String editPost(@PathVariable Integer id, @Validated(EditMember.class) Member member,
			Errors errors, Model model, HttpSession session) throws Exception {

		if (errors.hasErrors()) {
			model.addAttribute("groupList", groupService.getGroupList());
			return "member/memberEdit";
		}

		try {
			member.setId(id);
			memberService.editMember(member);
			session.setAttribute("memberName", member.getName());
			return "redirect:/home?status=edit";
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
				errors.rejectValue("email", ALREADY_REGIST);
			}
			model.addAttribute("groupList", groupService.getGroupList());
			return "member/memberEdit";
		}
	}

	/////////////////////
	///パスワード編集機能///
	/////////////////////
	@GetMapping("/passEdit/{id}")
	public String passEditGet(@PathVariable Integer id, Model model,
			HttpSession session) throws Exception {
		Member member = memberService.getMemberById(id);
		model.addAttribute("member", member);
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		return "member/passEdit";
	}

	@PostMapping("/passEdit/{id}")
	public String passEditPost(@PathVariable Integer id, @Validated(EditPassword.class) Member member,
			Errors errors, Model model, HttpSession session) throws Exception {
		// パスワードと確認用パスワードが一致しない場合
		if (!member.getPassword().equals(member.getPasswordConf())) {
			errors.rejectValue("password", NOT_SAME_PASS);
		}
		// 入力に不備
		if (errors.hasErrors()) {
			return "member/passEdit";
		}
		member.setId(id);	
		member.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
		memberService.editPass(member);	
		return "redirect:/home?status=passEdit";
	}

}
