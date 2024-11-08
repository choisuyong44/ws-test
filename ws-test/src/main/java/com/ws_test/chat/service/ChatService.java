package com.ws_test.chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws_test.chat.dto.ChatRoom;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatService {

	private final ObjectMapper objectMapper;
	private Map<String,ChatRoom> chatRoomMap;
	
	public ChatService(ObjectMapper objectMapper, Map<String, ChatRoom> chatRoomMap) {
		this.objectMapper = objectMapper;
		this.chatRoomMap = chatRoomMap;
	}
	
	// 빈 생성 시마다 생성자 호출 이후 생성
	@PostConstruct
	private void init() {
		chatRoomMap = new LinkedHashMap<>(); // Map 구현체인데, 순서대로 데이터 유지
	}
	
	public List<ChatRoom> findAllRoom(){
		return new ArrayList<>(chatRoomMap.values());
	}
	
	public ChatRoom findRoomById(String roomId) {
		return chatRoomMap.get(roomId);
	}
	
	public ChatRoom createRoom(String name) {
		String randomId = UUID.randomUUID().toString();
		ChatRoom chatRoom = ChatRoom.builder()
				.roomId(randomId)
				.name(name)
				.build();
		chatRoomMap.put(randomId, chatRoom);
		return chatRoom;
	}
	
	public <T> void sendMessage(WebSocketSession session,T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		}catch(IOException e) {
			log.error(e.getMessage(),e);
		}
	}
}
