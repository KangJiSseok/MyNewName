package com.mynewname.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mynewname.dto.ChatRequestDto;
import com.mynewname.dto.ChatResponseDto;
import com.mynewname.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping("/generate")
	public ResponseEntity<ChatResponseDto> generateName(@RequestBody ChatRequestDto requestDto) {
		ChatResponseDto response = chatService.generateEnglishName(requestDto);
		return ResponseEntity.ok(response);
	}
}
