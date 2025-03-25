package com.mynewname.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OpenAI에 넘겨줄 메시지 포맷
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiMessage {
	private String role;    // "system", "user", "assistant" 등
	private String content;
}