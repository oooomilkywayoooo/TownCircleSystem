package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Group;

@Mapper
public interface GroupDao extends GenericDao<Group> {
	public List<Group> selectAll()throws Exception;
	public Group selectById(Integer id) throws Exception;
	public void insert(Group group) throws Exception;
    public void update(Group group) throws Exception;
}
