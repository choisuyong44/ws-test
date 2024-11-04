package com.ws_test.chat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ws_test.chat.dto.ChatRoom;
import com.ws_test.chat.repository.ChatRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/chat")
@Controller
public class ChatRoomController {
	private final ChatRoomRepository chatRoomRepository;

	public ChatRoomController(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}
	
	@GetMapping("/room")
	public String rooms(Model model) {
		return "chat/room";
	}
	
	@GetMapping("/rooms")
	@ResponseBody
	public List<ChatRoom> room(){
		return chatRoomRepository.findAllRoom();
	}
	
	// 채팅방 생성
	@PostMapping("/room")
	@ResponseBody
	public ChatRoom chatRoom(@RequestParam String name) {
		ChatRoom chatroom = chatRoomRepository.createChatRoom(name);
		return chatroom;
	}
	
	// 채팅방 입장 화면
	@GetMapping("/room/enter/{roomId}")
	public String roomDetail(Model model, @PathVariable("roomId") String roomId) {
		model.addAttribute("roomId",roomId);
		return "chat/roomdetail";
	}
	
	// 특정 채팅방 조회
	@GetMapping("/room/{roodId}")
	@ResponseBody
	public ChatRoom roomInfo(@PathVariable("roomId") String roomId) {
		return chatRoomRepository.findRoomById(roomId);
	}
}
