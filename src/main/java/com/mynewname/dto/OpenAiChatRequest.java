package com.mynewname.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OpenAI ChatCompletion 요청에 필요한 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiChatRequest {
	private String model;
	private List<OpenAiMessage> messages;
	private double temperature; // 창의성 정도
	private int max_tokens;
	// 필요에 따라 top_p, frequency_penalty, presence_penalty, etc.
}