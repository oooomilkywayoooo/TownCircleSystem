package com.example.app.service;

import java.util.List;

import com.example.app.domain.Notification;

public interface NotificationService {
	public List<Notification>getNotificationList() throws Exception;
	public Notification getNotificationById(Integer id) throws Exception;
	public void addNotification(Notification notification) throws Exception;
	public void editNotification(Notification notification) throws Exception;
	public void deleteNotification(Notification notification) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<Notification> getListByPage(int page, int numPerPage) throws Exception;
}
