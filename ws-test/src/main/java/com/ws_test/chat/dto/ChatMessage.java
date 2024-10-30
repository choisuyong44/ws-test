package com.ws_test.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

	/*
	 * 채팅방 입장
	 * 채팅방 메시지 보내기
	 */
	
	public enum MessageType{
		ENTER,TALK
	}
	
	private MessageType type; // 메시지 타입
	private String roomId;
	private String sender;
	private String message;

}
