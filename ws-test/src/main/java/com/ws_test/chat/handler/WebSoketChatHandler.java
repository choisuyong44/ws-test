package com.ws_test.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws_test.chat.dto.ChatMessage;
import com.ws_test.chat.dto.ChatRoom;
import com.ws_test.chat.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSoketChatHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final ChatService chatService;

	public WebSoketChatHandler(ObjectMapper objectMapper, ChatService chatService) {
		this.objectMapper = objectMapper;
		this.chatService = chatService;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("payload {} : ",payload);
		ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
		ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
		room.handleActions(session, chatMessage, chatService);
	}
}
