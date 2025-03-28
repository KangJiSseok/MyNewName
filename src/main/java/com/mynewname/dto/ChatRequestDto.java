package com.mynewname.dto;

import jakarta.validation.constraints.Size;
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

	@Size(max = 50, message = "직업 또는 희망 직업은 50자 이하로 입력해주세요.")
	private String questionOne;

	@Size(max = 50, message = "파티 초대 반응은 50자 이하로 입력해주세요.")
	private String questionTwo;

	@Size(max = 50, message = "로또 당첨 시 행동은 50자 이하로 입력해주세요.")
	private String questionThree;

	@Size(max = 50, message = "가보고 싶은 과거 시대는 50자 이하로 입력해주세요.")
	private String questionFour;

	@Size(max = 50, message = "이름의 흔함 정도는 50자 이하로 입력해주세요.")
	private String questionFive;
}