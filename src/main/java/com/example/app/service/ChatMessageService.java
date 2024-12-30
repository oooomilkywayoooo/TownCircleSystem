package com.example.app.service;

import java.util.List;

import com.example.app.domain.ChatMessage;

public interface ChatMessageService {
	public List<ChatMessage>getChatMessageList() throws Exception;
	public ChatMessage getChatMessageById(Integer id) throws Exception;
	public void addChatMessage(ChatMessage chatMessage) throws Exception;
	public void deleteChatMessage(ChatMessage chatMessage) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<ChatMessage> getListByPage(int page, int numPerPage) throws Exception;
}
