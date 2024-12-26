package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.CircularBoardDao;
import com.example.app.dao.GenericDao;
import com.example.app.domain.CircularBoard;
@Service
public class CircularBoardServiceImpl extends GenericService<CircularBoard> implements CircularBoardService {
	@Autowired
	CircularBoardDao circularBoardDao;
	
	public CircularBoardServiceImpl(CircularBoardDao circularBoardDao) {
		this.circularBoardDao = circularBoardDao;
	}
	
	@Override
	protected GenericDao<CircularBoard> getDao() {
		return circularBoardDao;
	}
	
	@Override
	public List<CircularBoard> getCircularBoardList() throws Exception {
		return circularBoardDao.selectAll();
	}

	@Override
	public CircularBoard getCircularBoardById(Integer id) throws Exception {
		return circularBoardDao.selectById(id);
	}

	@Override
	public void addCircularBoard(CircularBoard circularBoard) throws Exception {
		circularBoardDao.insert(circularBoard);
	}

	@Override
	public void deleteCircularBoard(CircularBoard circularBoard) throws Exception {
		circularBoardDao.delete(circularBoard);
	}
}
