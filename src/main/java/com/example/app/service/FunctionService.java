package com.example.app.service;

import java.util.List;

import com.example.app.domain.Notification;

public interface FunctionService {
	// お知らせ情報操作
	public List<Notification>getNotificationList() throws Exception;
	public Notification getNotificationById(Integer id) throws Exception;
	public void addNotification(Notification notification) throws Exception;
	public void editNotification(Notification notification) throws Exception;
	public void deleteNotification(Notification notification) throws Exception;

}
