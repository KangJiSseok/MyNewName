package com.mynewname.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mynewname.dto.ChatRequestDto;
import com.mynewname.dto.ChatResponseDto;
import com.mynewname.service.ChatService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping("/generate")
	public Mono<ResponseEntity<ChatResponseDto>> generateName(@Valid @RequestBody ChatRequestDto requestDto) {
		log.info("/api/chat/generate");
		return chatService.generateEnglishName(requestDto)
			.map(response -> {
				log.info("response = {}", response);
				return ResponseEntity.ok(response);
			});
	}

}
