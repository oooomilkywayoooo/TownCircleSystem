package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.CircularBoard;

@Mapper
public interface CircularBoardDao extends GenericDao<CircularBoard> {
	public List<CircularBoard> selectAll() throws Exception;
	public CircularBoard selectById(Integer id) throws Exception;
	public CircularBoard selectByLatestData() throws Exception;
	public List<CircularBoard> selectByCreatedList(String dateStr) throws Exception;
	// 年月ごとのリスト
	public List<CircularBoard> selectByMonthList() throws Exception;
	public void insert(CircularBoard circularBoard) throws Exception;
	public void delete(CircularBoard circularBoard) throws Exception;
}
