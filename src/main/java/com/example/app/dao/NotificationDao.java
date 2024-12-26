package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Notification;

@Mapper
public interface NotificationDao extends GenericDao<Notification> {
	public List<Notification> selectAll() throws Exception;
	public Notification selectById(Integer id) throws Exception;
	public void insert(Notification notification) throws Exception;
	public void update(Notification notification) throws Exception;
	public void delete(Notification notification) throws Exception;

}
