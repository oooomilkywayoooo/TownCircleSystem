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

import com.example.app.domain.ChatMessage;
import com.example.app.domain.CircularBoard;
import com.example.app.domain.Group;
import com.example.app.domain.Member;
import com.example.app.domain.Notification;
import com.example.app.domain.Schedule;
import com.example.app.service.ChatMessageService;
import com.example.app.service.CircularBoardService;
import com.example.app.service.GroupService;
import com.example.app.service.MemberService;
import com.example.app.service.NotificationService;
import com.example.app.service.ScheduleService;

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
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	ChatMessageService chatMessageService; 

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
		model.addAttribute("memberList", memberService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("scheduleList", scheduleService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("chatList", chatMessageService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		// ページネーションの分岐
		if (tab == null || tab.equals("infoList")) {
			model.addAttribute("totalPages", notificationService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("boardList")) {
			model.addAttribute("totalPages", circularBoardService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("groupList")) {
			model.addAttribute("totalPages", groupService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("memberList")) {
			model.addAttribute("totalPages", memberService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("scheduleList")) {
			model.addAttribute("totalPages", scheduleService.getTotalPages(NUM_PER_PAGE));
		} else if (tab.equals("chatList")) {
			model.addAttribute("totalPages", chatMessageService.getTotalPages(NUM_PER_PAGE));
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
	
	/////////////////
	///会員管理機能////
	/////////////////
	@GetMapping("/memberShow/{id}")
	public String memberShow(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("member", memberService.getMemberById(id));
		return "admin/show/memberShow";
	}
	
	@GetMapping("/memberDelete/{id}")
	public String memberDelete(@PathVariable Integer id, Model model) throws Exception {
		Member member = memberService.getMemberById(id);
		member.setDeleteFlg(DELETE_FLG);
		memberService.deleteMember(member);
		return "redirect:/admin/home?tab=memberList&status=delete";
	}

	@GetMapping("/memberGroupEdit/{id}")
	public String memberGroupEditGet(@PathVariable Integer id,  Model model) throws Exception {
		Member member = memberService.getMemberById(id);
		model.addAttribute("member", member);
		model.addAttribute("groupList", groupService.getGroupList());
		return "admin/memberGroupEdit";
	}
	
	@PostMapping("/memberGroupEdit/{id}")
	public String memberGroupEditPost(@PathVariable Integer id, @RequestParam Integer groupId, 
					Model model) throws Exception {
		Member member = memberService.getMemberById(id);
		Group group = groupService.getGroupById(groupId);
		member.setGroup(group);
		memberService.editMember(member);
		return "redirect:/admin/home?tab=memberList&status=edit";
	}
	
	///////////////////////
	///スケジュール管理機能///
	//////////////////////
	@GetMapping("/scheduleRegister")
	public String scheduleAddGet(Model model) {
		model.addAttribute("schedule", new Schedule());
		return "admin/scheduleAdd";
	}

	@PostMapping("/scheduleRegister")
	public String scheduleAddPost(@Valid Schedule schedule,
			Errors errors, Model model) throws Exception {
		if (errors.hasErrors()) {
			return "admin/scheduleAdd";
		}

		scheduleService.addSchedule(schedule);
		return "redirect:/admin/home?tab=scheduleList&status=add";
	}

	@GetMapping("/scheduleEdit/{id}")
	public String scheduleEditGet(@PathVariable Integer id, Model model) throws Exception {
		Schedule schedule = scheduleService.getScheduleById(id);
		model.addAttribute("schedule", schedule);
		return "admin/scheduleEdit";
	}

	@PostMapping("/scheduleEdit/{id}")
	public String scheduleEditPost(@PathVariable Integer id,
			@Valid Schedule schedule,
			Errors errors, Model model) throws Exception {
		if (errors.hasErrors()) {
			return "admin/scheduleEdit";
		}
		schedule.setId(id);
		scheduleService.editSchedule(schedule);
		return "redirect:/admin/home?tab=scheduleList&status=edit";
	}

	@GetMapping("/scheduleDelete/{id}")
	public String scheduleDelete(@PathVariable Integer id, Model model) throws Exception {
		Schedule schedule = scheduleService.getScheduleById(id);
		schedule.setDeleteFlg(DELETE_FLG);
		scheduleService.deleteSchedule(schedule);
		return "redirect:/admin/home?tab=scheduleList&status=delete";
	}
	
	///////////////
	///チャット機能//
	///////////////
	@GetMapping("/chatDelete/{id}")
	public String chatDelete(@PathVariable Integer id, Model model) throws Exception {
		ChatMessage chat = chatMessageService.getChatMessageById(id);
		chat.setDeleteFlg(DELETE_FLG);
		chatMessageService.deleteChatMessage(chat);
		return "redirect:/admin/home?tab=chatList&status=delete";
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
