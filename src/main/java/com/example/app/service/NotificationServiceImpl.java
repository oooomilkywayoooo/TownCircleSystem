package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.NotificationDao;
import com.example.app.domain.Notification;

@Service
public class NotificationServiceImpl extends GenericService<Notification> implements NotificationService {
	@Autowired
	NotificationDao notificationDao;
	
	public NotificationServiceImpl(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}
	
	@Override
	protected GenericDao<Notification> getDao() {
		return notificationDao;
	}

	@Override
	public List<Notification> getNotificationList() throws Exception {
		return notificationDao.selectAll();
	}
	
	@Override
	public List<Notification> getLimitedList() throws Exception {
		return notificationDao.selectLimitedList();
	}

	@Override
	public Notification getNotificationById(Integer id) throws Exception {
		return notificationDao.selectById(id);
	}

	@Override
	public void addNotification(Notification notification) throws Exception {
		notificationDao.insert(notification);
		
	}

	@Override
	public void editNotification(Notification notification) throws Exception {
		notificationDao.update(notification);
		
	}

	@Override
	public void deleteNotification(Notification notification) throws Exception {
		notificationDao.delete(notification);
		
	}
	
}
