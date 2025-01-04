package com.example.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.CircularBoard;
import com.example.app.domain.ReadStatus;
import com.example.app.service.CircularBoardService;
import com.example.app.service.NotificationService;
import com.example.app.service.ScheduleService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	NotificationService notificationService;
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	CircularBoardService circularBoardService;
	
	//////////////
	///ホーム画面///
	//////////////
	@GetMapping("/home")
	public String home(Model model, HttpSession session,
			@RequestParam(name = "status", required = false) String status) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// 登録・編集などのステータス
		model.addAttribute("statusMessage", getStatusMessage(status));
		// リストの表示
		model.addAttribute("infoList", notificationService.getLimitedList());
		model.addAttribute("scheduleList", scheduleService.getCurrentMonthList());
		return "member/home";
	}
	
	//////////////
	///回覧板画面///
	//////////////
	@GetMapping("/board")
	public String board(Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// 最新の回覧板登録日時を取得
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		CircularBoard latestBoard = circularBoardService.getLatestData();
		String latestDate = df.format(latestBoard.getCreated());
		// 最新日時に登録された回覧板リストを取得し、表示する
		model.addAttribute("latestDate", latestBoard.getCreated()); // 最新日
		model.addAttribute("boardList", circularBoardService.getLatestList(latestDate));
		// 既読ステータスを検索
		Integer memberId = (Integer)session.getAttribute("memberId");
		List<ReadStatus> readStatusList = circularBoardService.getStatusByMemberId(memberId);
		
		// 既読ステータスに保存された回覧板IDのリストを作成
		List<Integer> readList = new ArrayList<>();
		for(ReadStatus read : readStatusList) {
			readList.add(read.getCircularBoardId());
		}
		model.addAttribute("readList", readList);
		return "member/board";
	}
	
	@GetMapping("/board/{id}")
	public String boardRead(@PathVariable Integer id, Model model, HttpSession session) throws Exception {
		// 会員ID取得
		Integer memberId = (Integer)session.getAttribute("memberId");
		// 既読ステータス登録
		ReadStatus readStatus = new ReadStatus();
		readStatus.setMemberId(memberId);
		readStatus.setCircularBoardId(id);
		circularBoardService.addReadStatus(readStatus);
		return "redirect:/board";
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
		case "passEdit":
			message = "パスワードを更新しました。";
			break;
		}
		return message;
	}
}
