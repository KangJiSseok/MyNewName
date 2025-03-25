package com.mynewname.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import com.mynewname.dto.OpenAiChatRequest;
import com.mynewname.dto.OpenAiChatResponse;
import com.mynewname.dto.OpenAiMessage;

@Service
public class OpenAiService {

	private final String apiKey;
	private final String apiUrl;
	private final WebClient webClient;

	public OpenAiService(
		@Value("${spring.ai.openai.api-key}") String apiKey,
		@Value("${spring.ai.openai.url}") String apiUrl
	) {
		this.apiKey = apiKey;
		this.apiUrl = apiUrl;

		this.webClient = WebClient.builder()
			.baseUrl(apiUrl)
			.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
			.build();
	}

	/**
	 * 실제 ChatGPT API (ChatCompletion) 호출
	 */
	public String getChatCompletion(String prompt) {
		// ChatCompletion에 맞게 메시지 리스트 작성
		OpenAiChatRequest requestBody = new OpenAiChatRequest(
			"gpt-3.5-turbo", // 또는 "gpt-4"
			List.of(
				new OpenAiMessage("system", "You are a helpful assistant."),
				new OpenAiMessage("user", prompt)
			),
			0.7,
			200
		);

		// WebClient를 사용하여 비동기로 요청
		OpenAiChatResponse response = this.webClient.post()
			.bodyValue(requestBody)
			.retrieve()
			.bodyToMono(OpenAiChatResponse.class)
			.block(); // block()은 동기식으로 수행하기 위해 사용, 실제 서비스에선 주의

		if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
			return response.getChoices().get(0).getMessage().getContent().trim();
		}
		return "";
	}
}