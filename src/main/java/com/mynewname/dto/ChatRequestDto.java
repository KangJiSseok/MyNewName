package com.mynewname.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자가 입력한 폼 데이터 등을 받는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
	private Integer age;
	private String gender;      // "male", "female", "neutral" 등
	private String mbti;
	private String job;
	private String uniqueness;  // 본인의 독특한 점

	// 대화(채팅) 컨텍스트 전체를 넘기고 싶다면 추가 필드를 선언 가능
	// private List<Message> messages;
}
