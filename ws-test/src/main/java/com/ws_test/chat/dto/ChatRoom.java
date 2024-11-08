package com.ws_test.chat.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.ws_test.chat.service.ChatService;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
public class ChatRoom {
	private String roomId;
	private String name;
	private Set<WebSocketSession> sessionSet = new HashSet<>();
	
	@Builder
	public ChatRoom(String roomId, String name) {
		this.roomId = roomId;
		this.name = name;
	}
	
	public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
		log.info("handlerAction Error: "+ chatMessage.getRoomId());
		if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
			sessionSet.add(session);
			chatMessage.setMessage((chatMessage.getSender())+ "님이 입장했습니다.");
		}
		
		sendMessage(chatMessage,chatService);
	}
	
	public <T> void sendMessage(T message, ChatService chatservice) {
		sessionSet.parallelStream().forEach(session ->chatservice.sendMessage(session, message));
	}
}
