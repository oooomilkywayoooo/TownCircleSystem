package com.example.app.service;

import java.util.List;

import com.example.app.domain.Opinion;

public interface OpinionService {
	public List<Opinion>getOpinionList() throws Exception;
	public Opinion getOpinionById(Integer id) throws Exception;
	public void addOpinion(Opinion opinion) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<Opinion> getListByPage(int page, int numPerPage) throws Exception;
}
