package com.example.app.service;

import java.util.List;

import com.example.app.domain.CircularBoard;
import com.example.app.domain.ReadStatus;

public interface CircularBoardService {
	public List<CircularBoard>getCircularBoardList() throws Exception;
	public CircularBoard getCircularBoardById(Integer id) throws Exception;
	public void addCircularBoard(CircularBoard circularBoard) throws Exception;
	public void deleteCircularBoard(CircularBoard circularBoard) throws Exception;
	public CircularBoard getLatestData() throws Exception;
	public List<CircularBoard>getCreatedList(String dateStr) throws Exception;
	// 年月ごとのリスト取得
	public List<CircularBoard>getMonthList() throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<CircularBoard> getListByPage(int page, int numPerPage) throws Exception;
	// 既読ステータス
	public List<ReadStatus>getStatusByMemberId(Integer id) throws Exception;
	public void addReadStatus(ReadStatus readStatus) throws Exception;
}
