package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.ChatMessageDao;
import com.example.app.dao.GenericDao;
import com.example.app.domain.ChatMessage;

@Service
public class ChatMessageServiceImpl extends GenericService<ChatMessage> implements ChatMessageService {
	@Autowired
	ChatMessageDao chatMessageDao;
	
	public ChatMessageServiceImpl(ChatMessageDao chatMessageDao) {
		this.chatMessageDao = chatMessageDao;
	}
	
	@Override
	protected GenericDao<ChatMessage> getDao() {
		return chatMessageDao;
	}

	@Override
	public List<ChatMessage> getChatMessageList() throws Exception {
		return chatMessageDao.selectAll();
	}

	@Override
	public ChatMessage getChatMessageById(Integer id) throws Exception {
		return chatMessageDao.selectById(id);
	}

	@Override
	public void addChatMessage(ChatMessage chatMessage) throws Exception {
		chatMessageDao.insert(chatMessage);
	}

	@Override
	public void deleteChatMessage(ChatMessage chatMessage) throws Exception {
		chatMessageDao.delete(chatMessage);
	}

}
