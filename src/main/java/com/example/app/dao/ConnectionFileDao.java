package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ConnectionFile;

@Mapper
public interface ConnectionFileDao extends GenericDao<ConnectionFile> {
	public List<ConnectionFile> selectAll() throws Exception;
	public ConnectionFile selectById(Integer id) throws Exception;
	public ConnectionFile selectByLatestData() throws Exception;
	public void insert(ConnectionFile connectionFile) throws Exception;
	public void delete(ConnectionFile connectionFile) throws Exception;
}
