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
import com.example.app.domain.Notification;
import com.example.app.service.CircularBoardService;
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
	
	
	/**
	 * 　ホーム画面/一覧機能
	 * @param page		一覧の表示ページ
	 * @param status	登録・追加・削除のステータス
	 * @param model
	 * @return			ホーム画面
	 */
	@GetMapping("/home")
	public String home(
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name="status", required=false) String status, 
			Model model) throws Exception{
		model.addAttribute("infoList", notificationService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("boardList", circularBoardService.getListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		model.addAttribute("totalPages", notificationService.getTotalPages(NUM_PER_PAGE));
		model.addAttribute("totalPages", circularBoardService.getTotalPages(NUM_PER_PAGE));
		model.addAttribute("statusMessage", getStatusMessage(status));
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
		if(errors.hasErrors()) {
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
		if(errors.hasErrors()) {
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
	public String boardAddPost(@Valid CircularBoard circularBoard, 
			Errors errors, Model model) throws Exception {
		if(errors.hasErrors()) {
			return "admin/boardAdd";
		}
		// バリデーション
		MultipartFile upfile = circularBoard.getUpfile();
		if (!upfile.isEmpty()) {
		// 画像か否か判定する
		String type = upfile.getContentType();
		if (!type.startsWith("image/")) {
		// 画像ではない場合、エラーメッセージを表示
		errors.rejectValue("upfile", "error.not_image_file");
		}
		}
		circularBoardService.addCircularBoard(circularBoard);
		return "redirect:/admin/home?status=add";
	}
	
	/**
	 * ステータスメッセージ生成メソッド
	 * @param status　登録・追加・削除ステータス
	 * @return　対応するメッセージ
	 */
	private String getStatusMessage(String status) {
		String message = null;
		if(status == null) {
			return message;
		}
		
		switch(status) {
		case "add":
			message = "追加しました。";
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
