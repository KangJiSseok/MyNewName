package com.mynewname.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI가 생성한 영어 이름과 간단한 설명
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {
	private String name;
	private String explanation;
}