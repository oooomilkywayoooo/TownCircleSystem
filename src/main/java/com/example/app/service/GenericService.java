package com.example.app.service;

import java.util.List;

import com.example.app.dao.GenericDao;

public abstract class GenericService<T> {
	// 各エンティティ毎のDAOを提供
	protected abstract GenericDao<T> getDao();
	
	public int getTotalPages(int numPerPage) throws Exception {
        double totalNum = (double) getDao().count();
        return (int) Math.ceil(totalNum / numPerPage);
    }
	
	public List<T> getListByPage(int page, int numPerPage) throws Exception {
        int offset = numPerPage * (page - 1);
        return getDao().selectLimited(offset, numPerPage);
    }
}
