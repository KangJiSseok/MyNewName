package com.mynewname.dto;


import lombok.Data;

import java.util.List;

@Data
public class OpenAiChatResponse {
	private String id;
	private String object;
	private Long created;
	private List<Choice> choices;

	@Data
	public static class Choice {
		private int index;
		private OpenAiMessage message;
		private String finish_reason;
	}
}