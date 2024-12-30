package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ChatMessage;

@Mapper
public interface ChatMessageDao extends GenericDao<ChatMessage> {
	public List<ChatMessage> selectAll() throws Exception;
	public ChatMessage selectById(Integer id) throws Exception;
	public void insert(ChatMessage chatMessage) throws Exception;
	public void delete(ChatMessage chatMessage) throws Exception;
}
