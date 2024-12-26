package com.example.app.service;

import java.util.List;

import com.example.app.domain.CircularBoard;

public interface CircularBoardService {
	public List<CircularBoard>getCircularBoardList() throws Exception;
	public CircularBoard getCircularBoardById(Integer id) throws Exception;
	public void addCircularBoard(CircularBoard circularBoard) throws Exception;
	public void deleteCircularBoard(CircularBoard circularBoard) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<CircularBoard> getListByPage(int page, int numPerPage) throws Exception;
}
