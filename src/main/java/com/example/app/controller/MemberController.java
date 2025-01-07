package com.example.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Admin;
import com.example.app.domain.ChatMessage;
import com.example.app.domain.CircularBoard;
import com.example.app.domain.Member;
import com.example.app.domain.Opinion;
import com.example.app.domain.ReadStatus;
import com.example.app.service.AdminService;
import com.example.app.service.ChatMessageService;
import com.example.app.service.CircularBoardService;
import com.example.app.service.ConnectionFileService;
import com.example.app.service.MemberService;
import com.example.app.service.NotificationService;
import com.example.app.service.OpinionService;
import com.example.app.service.QuestionnaireService;
import com.example.app.service.ScheduleService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MemberController {
	// 現在の月を取得
	Calendar cl = Calendar.getInstance();
	String currentYearMonth = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + 1;
	Integer currentMonth = cl.get(Calendar.MONTH) + 1;

	@Autowired
	NotificationService notificationService;
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	CircularBoardService circularBoardService;
	@Autowired
	ChatMessageService chatService;
	@Autowired
	ConnectionFileService fileService;
	@Autowired
	QuestionnaireService questionService;
	@Autowired
	MemberService memberService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	AdminService adminService;

	//////////////
	///ホーム画面///
	//////////////
	@GetMapping("/home")
	public String home(Model model, HttpSession session,
			@RequestParam(name = "status", required = false) String status) throws Exception {
		// セッションに当月の情報を格納
		session.setAttribute("currentMonth", currentMonth);
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
		model.addAttribute("boardList", circularBoardService.getCreatedList(latestDate));
		// 既読ステータスを検索
		Integer memberId = (Integer) session.getAttribute("memberId");
		List<ReadStatus> readStatusList = circularBoardService.getStatusByMemberId(memberId);

		// 既読ステータスに保存された回覧板IDのリストを作成
		List<Integer> readList = new ArrayList<>();
		for (ReadStatus read : readStatusList) {
			readList.add(read.getCircularBoardId());
		}
		model.addAttribute("readList", readList);
		model.addAttribute("currentYearMonth", currentYearMonth);
		return "member/board";
	}

	/**
	 * 既読情報を登録
	 * @param id　回覧板ID
	 * @param model
	 * @param session　会員情報
	 * @return　回覧板画面
	 * @throws Exception
	 */
	@GetMapping("/board/{id}")
	public String boardRead(@PathVariable Integer id, Model model, HttpSession session) throws Exception {
		// 会員ID取得
		Integer memberId = (Integer) session.getAttribute("memberId");
		// 既読ステータス登録
		ReadStatus readStatus = new ReadStatus();
		readStatus.setMemberId(memberId);
		readStatus.setCircularBoardId(id);
		circularBoardService.addReadStatus(readStatus);
		return "redirect:/board";
	}

	@GetMapping("/prevboard/{selectMonth}")
	public String prevBoard(@PathVariable String selectMonth, Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// 年月取得
		model.addAttribute("yearMonth", circularBoardService.getMonthList());
		// 選択された月に登録されたリストを取得
		model.addAttribute("prevList", circularBoardService.getCreatedList(selectMonth));
		return "member/prevboard";
	}

	////////////////////
	///スケジュール画面////
	////////////////////
	@GetMapping("/schedule/{selectMonth}")
	public String schedule(@PathVariable String selectMonth, Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// 選択月の取得
		String month = selectMonth.substring(5, 7);
		model.addAttribute("month", month);
		// 選択月のスケジュール取得
		model.addAttribute("scheduleList", scheduleService.getByEventList(selectMonth));
		return "member/schedule";
	}

	/////////////////
	///チャット画面////
	/////////////////
	@GetMapping("/chat")
	public String chat(Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// チャットメッセージフォームの受け取り
		model.addAttribute("chatMessage", new ChatMessage());
		// DBから全てのチャットメッセージを取得、表示
		model.addAttribute("chatList", chatService.getChatMessageList());
		return "member/chat";
	}
	
	@PostMapping("/chat")
	public String chatPost(@Valid ChatMessage chatMessage, 
					Errors errors, HttpSession session, Model model) throws Exception {
		if(errors.hasErrors()) {
			return "member/chat";
		}
		// 送信者IDを取得
		Member member = memberService.getMemberById((Integer)session.getAttribute("memberId"));
		chatMessage.setMember(member);
		// メッセージをDBに保存
		chatService.addChatMessage(chatMessage);
		return "redirect:/chat";
	}

	/////////////////
	///関連資料画面////
	/////////////////
	@GetMapping("/file")
	public String file(Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// 全ての有効な関連資料を取得
		model.addAttribute("fileList", fileService.getConnectionFileList());
		return "member/file";
	}

	///////////////////
	///アンケート画面////
	///////////////////
	@GetMapping("/question")
	public String question(Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// 全ての有効なアンケートを取得
		model.addAttribute("questionList", questionService.getQuestionnaireList());
		return "member/question";
	}

	////////////////
	///ご意見画面////
	///////////////
	@GetMapping("/opinionbox")
	public String opinion(Model model, HttpSession session) {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		// フォーム受け取り
		model.addAttribute("opinion", new Opinion());
		return "member/opinionbox";
	}

	@PostMapping("/opinionbox")
	public String opinionPost(@RequestParam(name = "anonymous", defaultValue = "false") boolean anonymous,
			@Valid Opinion opinion, Errors errors, Model model, HttpSession session) throws Exception {
		// 入力に不備
		if (errors.hasErrors()) {
			return "member/opinionbox";
		}

		if (anonymous) {
			Integer memberId = (Integer) session.getAttribute("memberId");
			Member member = memberService.getMemberById(memberId);
			opinion.setMember(member);
		}
		opinionService.addOpinion(opinion);
		return "redirect:/home?status=add";
	}

	/////////////////
	///緊急連絡画面////
	/////////////////
	@GetMapping("/contact")
	public String contact(Model model, HttpSession session) throws Exception {
		// ヘッダー用ログインメンバーの会員idと会員名
		model.addAttribute("currentId", session.getAttribute("memberId"));
		model.addAttribute("currentName", session.getAttribute("memberName"));
		Admin admin = adminService.getAdminByLoginId("admin1");
		model.addAttribute("admin", admin);
		return "member/contact";
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
