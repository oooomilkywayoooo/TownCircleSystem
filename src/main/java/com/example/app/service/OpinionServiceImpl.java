package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.OpinionDao;
import com.example.app.domain.Opinion;

@Service
public class OpinionServiceImpl extends GenericService<Opinion> implements OpinionService {
	
	@Autowired
	OpinionDao opinionDao;
	
	public OpinionServiceImpl(OpinionDao opinionDao) {
		this.opinionDao = opinionDao;
	}
	
	@Override
	protected GenericDao<Opinion> getDao() {
		return opinionDao;
	}
	
	@Override
	public List<Opinion> getOpinionList() throws Exception {
		return opinionDao.selectAll();
	}

	@Override
	public Opinion getOpinionById(Integer id) throws Exception {
		return opinionDao.selectById(id);
	}

	@Override
	public void addOpinion(Opinion opinion) throws Exception {
		opinionDao.insert(opinion);

	}

}
