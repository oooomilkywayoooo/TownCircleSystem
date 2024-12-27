package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.CircularBoard;
import com.example.app.domain.Group;
import com.example.app.domain.Notification;
import com.example.app.service.CircularBoardService;
import com.example.app.service.GroupService;
import com.example.app.service.MemberService;
import com.example.app.service.NotificationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
	// 1ページ5件表示
	private static final int NUM_PER_PAGE = 5;
	// 削除フラグ
	private static final int DELETE_FLG = 1;

	@Autowired
	NotificationService notificationService;
	@Autowired
	CircularBoardService circularBoardService;
	@Autowired
	GroupService groupService;
	@Autowired
	MemberService memberService;

	/**
	 * 　ホーム画面/一覧機能
	 * @param page		一覧の表示ページ
	 * @param status	登録・追加・削除のステータス
	 * @param model
	 * @return			ホーム画面
	 */
	@GetMapping("/home")
	public String home(
			@RequestParam(name = "tab", required = false) String tab,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "status", required = false) String status,
			Model model) throws Exception {
		model.addAttribute("infoList", notificationService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("boardList", circularBoardService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("groupList", groupService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		// ページネーションの分岐
		if (tab == null || tab.equals("infoList")) {
			model.addAttribute("totalPages", notificationService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("boardList")) {
			model.addAttribute("totalPages", circularBoardService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("groupList")) {
			model.addAttribute("totalPages", groupService.getTotalPages(NUM_PER_PAGE));
		}

		model.addAttribute("statusMessage", getStatusMessage(status));
		model.addAttribute("tab", tab);
		return "admin/adminHome";
	}

	///////////////
	///お知らせ機能//
	///////////////
	@GetMapping("/infoRegister")
	public String notificationAddGet(Model model) {
		model.addAttribute("notification", new Notification());
		return "admin/infoAdd";
	}

	@PostMapping("/infoRegister")
	public String notificationAddPost(@Valid Notification notification,
			Errors errors, Model model) throws Exception {
		if (errors.hasErrors()) {
			return "admin/infoAdd";
		}

		notificationService.addNotification(notification);
		return "redirect:/admin/home?status=add";
	}

	@GetMapping("/infoEdit/{id}")
	public String notificationEditGet(@PathVariable Integer id, Model model) throws Exception {
		Notification notification = notificationService.getNotificationById(id);
		model.addAttribute("notification", notification);
		return "admin/infoEdit";
	}

	@PostMapping("/infoEdit/{id}")
	public String notificationEditPost(@PathVariable Integer id,
			@Valid Notification notification,
			Errors errors, Model model) throws Exception {
		if (errors.hasErrors()) {
			return "admin/infoEdit";
		}
		notification.setId(id);
		notificationService.editNotification(notification);
		return "redirect:/admin/home?status=edit";
	}

	@GetMapping("/infoDelete/{id}")
	public String notificationDelete(@PathVariable Integer id, Model model) throws Exception {
		Notification notification = notificationService.getNotificationById(id);
		notification.setDeleteFlg(DELETE_FLG);
		notificationService.deleteNotification(notification);
		return "redirect:/admin/home?status=delete";
	}

	///////////////
	///回覧板機能////
	///////////////
	@GetMapping("/boardRegister")
	public String boardAddGet(Model model) {
		model.addAttribute("circularBoard", new CircularBoard());
		return "admin/boardAdd";
	}

	@PostMapping("/boardRegister")
	public String boardAddPost(
			@Valid CircularBoard circularBoard,
			Errors errors, Model model) throws Exception {
		// バリデーション
		MultipartFile upfile = circularBoard.getUpfile();
		if(upfile.isEmpty()) {
			// 画像が選択されていない場合、エラーメッセージを表示
			errors.rejectValue("upfile", "error.file_isEnpty");
		}else {
			// 画像か否か判定する
			String type = upfile.getContentType();
			if (!type.startsWith("image/")) {
				// 画像ではない場合、エラーメッセージを表示
				errors.rejectValue("upfile", "error.not_image_file");
			}
		}
		
		if (errors.hasErrors()) {
			return "admin/boardAdd";
		}
		
		circularBoardService.addCircularBoard(circularBoard);
		return "redirect:/admin/home?tab=boardList&status=add";
	}

	@GetMapping("/boardDelete/{id}")
	public String boardDelete(
			@PathVariable Integer id, Model model) throws Exception {
		CircularBoard circularBoard = circularBoardService.getCircularBoardById(id);
		circularBoard.setDeleteFlg(DELETE_FLG);
		circularBoardService.deleteCircularBoard(circularBoard);
		return "redirect:/admin/home?tab=boardList&status=delete";
	}

	////////////////////
	///グループ管理機能////
	////////////////////
	@GetMapping("/groupRegister")
	public String groupAddGet(Model model) {
		model.addAttribute("group", new Group());
		return "admin/groupAdd";
	}

	@PostMapping("/groupRegister")
	public String groupAddPost(@Valid Group group,
			Errors errors, Model model) throws Exception {
		if (errors.hasErrors()) {
			return "admin/groupAdd";
		}

		groupService.addGroup(group);
		return "redirect:/admin/home?tab=groupList&status=add";
	}

	@GetMapping("/groupShow/{id}")
	public String groupShow(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("group", groupService.getGroupById(id));
		model.addAttribute("memberBygroupList", memberService.getMemberByGroupId(id));

		return "admin/show/groupShow";

	}
	
	@GetMapping("/groupFlgEdit/{id}")
	public String groupFlgEdit(@PathVariable Integer id, Model model) throws Exception {
		Group group = groupService.getGroupById(id);
		// 削除フラグのオンオフを実行
		if(group.getDeleteFlg() == 0) {
			group.setDeleteFlg(DELETE_FLG);
		}else {
			group.setDeleteFlg(0);
		}
		groupService.flgChangeGroup(group);
		return "redirect:/admin/home?tab=groupList&status=change";
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
			message = "追加しました。";
			break;
		case "edit":
			message = "更新しました。";
			break;
		case "delete":
			message = "削除しました。";
			break;
		case "change":
			message = "フラグを変更しました。";
			break;
		}
		return message;
	}
}
