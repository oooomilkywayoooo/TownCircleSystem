package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Admin;

@Mapper
public interface AdminDao {
	Admin selectByLoginId(String loginId) throws Exception;
}
