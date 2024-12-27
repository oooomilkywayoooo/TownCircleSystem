package com.example.app.service;

import java.util.List;

import com.example.app.domain.Group;

public interface GroupService {
	public List<Group>getGroupList() throws Exception;
	public Group getGroupById(Integer id) throws Exception;
	public void addGroup(Group group) throws Exception;
	public void flgChangeGroup(Group group) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<Group> getListByPage(int page, int numPerPage) throws Exception;
}
