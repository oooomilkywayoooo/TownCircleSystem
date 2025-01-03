package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	
	//////////////
	///ホーム画面///
	//////////////
	@GetMapping("/home")
	public String home(Model model, HttpSession session,
			@RequestParam(name = "status", required = false) String status) {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		model.addAttribute("statusMessage", getStatusMessage(status));
		return "member/home";
	}
	
	/**
	 * ステータスメッセージ生成メソッド
	 * @param status　登録・追加・削除ステータス
	 * @return　対応するメッセージ
	 */
	private String getStatusMessage(String status) {
		String message = null;
		if (status == null) {
			return message;
		}

		switch (status) {
		case "add":
			message = "登録しました。";
			break;
		case "edit":
			message = "更新しました。";
			break;
		case "delete":
			message = "削除しました。";
			break;
		}
		return message;
	}
}
