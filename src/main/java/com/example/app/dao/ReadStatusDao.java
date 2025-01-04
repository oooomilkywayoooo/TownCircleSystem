package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ReadStatus;

@Mapper
public interface ReadStatusDao {
	public List<ReadStatus> selectByMemberId(Integer id) throws Exception;
	public void insert(ReadStatus readStatus) throws Exception;
}
