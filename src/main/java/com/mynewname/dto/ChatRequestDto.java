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
	private String gender;
	private String questionOne;
	private String questionTwo;
	private String questionThree;
	private String questionFour;
	private String questionFive;

}
