package com.mynewname.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mynewname.dto.ChatRequestDto;
import com.mynewname.dto.ChatResponseDto;
import com.mynewname.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping("/generate")
	public ResponseEntity<ChatResponseDto> generateName(@RequestBody ChatRequestDto requestDto) {
		log.info("generate");
		ChatResponseDto response = chatService.generateEnglishName(requestDto);
		log.info("response = {}", response);
		return ResponseEntity.ok(response);
	}
}
