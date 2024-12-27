package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.GroupDao;
import com.example.app.domain.Group;

@Service
public class GroupServiceImpl extends GenericService<Group> implements GroupService {
	
	@Autowired
	GroupDao groupDao;
	
	public GroupServiceImpl(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	@Override
	protected GenericDao<Group> getDao() {
		return groupDao;
	}

	@Override
	public List<Group> getGroupList() throws Exception {
		return groupDao.selectAll();
	}

	@Override
	public Group getGroupById(Integer id) throws Exception {
		return groupDao.selectById(id);
	}

	@Override
	public void addGroup(Group group) throws Exception {
		groupDao.insert(group);
	}

	@Override
	public void flgChangeGroup(Group group) throws Exception {
		groupDao.update(group);
	}
	
}
