package com.ws_test.chat.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.ws_test.chat.dto.ChatMessage;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {

	private final SimpMessageSendingOperations messagingTemplate;

	public ChatController(SimpMessageSendingOperations messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/chat/message")
	public void message(ChatMessage message) {
		if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
			message.setMessage(message.getSender()+"님이 입장하셨습니다.");
		}
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(),message);
	}
}
