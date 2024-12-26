package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GenericDao<T> {
	// ページ分割機能用
	public Long count() throws Exception;
	List<T> selectLimited(@Param("offset") int offset, @Param("num") int num) throws Exception;
}
