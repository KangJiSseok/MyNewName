package com.mynewname.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mynewname.dto.OpenAiChatRequest;
import com.mynewname.dto.OpenAiChatResponse;
import com.mynewname.dto.OpenAiMessage;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OpenAiService {
	private final WebClient webClient;
	private final String model;
	private final double temperature;
	private final int maxToken;

	public OpenAiService(
		WebClient webClient,
		@Value("${spring.ai.openai.chat.options.model}") String model,
		@Value("${spring.ai.openai.chat.options.temperature}") double temperature,
		@Value("${spring.ai.openai.chat.options.max-tokens}") int maxToken
	) {
		this.webClient = webClient;
		this.model = model;
		this.temperature = temperature;
		this.maxToken = maxToken;
	}
	public Mono<String> getChatCompletionAsync(String prompt) {
		OpenAiChatRequest requestBody = new OpenAiChatRequest(
			model,
			List.of(new OpenAiMessage("user", prompt)),
			temperature,
			maxToken
		);

		return this.webClient.post()
			.bodyValue(requestBody)
			.retrieve()
			.bodyToMono(OpenAiChatResponse.class)
			.map(response -> {
				if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
					return response.getChoices().get(0).getMessage().getContent().trim();
				}
				return "";
			});
	}
}
