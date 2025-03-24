package com.mynewname.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

@Configuration
public class AiChatConfig {

	private final ChatClient.Builder chatClientBuilder;

	public AiChatConfig(ChatClient.Builder chatClientBuilder) {
		this.chatClientBuilder = chatClientBuilder;
	}

	@Bean
	public ChatClient chatClient() {
		return chatClientBuilder.build();
	}
}
