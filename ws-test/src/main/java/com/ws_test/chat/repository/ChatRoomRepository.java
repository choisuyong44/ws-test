package com.ws_test.chat.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ws_test.chat.dto.ChatRoom;

import jakarta.annotation.PostConstruct;

@Repository
public class ChatRoomRepository {

	private Map<String,ChatRoom> chatRoomMap;
	
	public ChatRoomRepository() {};

	@PostConstruct
	private void init() {
		chatRoomMap = new LinkedHashMap<>();
	}
	
	public List<ChatRoom> findAllRoom(){
		// 채팅방 생성 순서 최근 순으로 반환
		List<ChatRoom> chatRoomList = new ArrayList<>(chatRoomMap.values());
		Collections.reverse(chatRoomList);
		return chatRoomList;
	}
	
	public ChatRoom findRoomById(String id) {
		return chatRoomMap.get(id);
	}
	
	public ChatRoom createChatRoom(String name) {
		ChatRoom chatRoom = ChatRoom.create(name);
		chatRoomMap.put(chatRoom.getRoomId(),chatRoom);
		return chatRoom;
	}
}
