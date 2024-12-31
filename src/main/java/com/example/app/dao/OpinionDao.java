package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Opinion;

@Mapper
public interface OpinionDao extends GenericDao<Opinion> {
	public List<Opinion> selectAll() throws Exception;
	public Opinion selectById(Integer id) throws Exception;
	public void insert(Opinion opinion) throws Exception;
}
