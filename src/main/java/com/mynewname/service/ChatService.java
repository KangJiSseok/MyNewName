package com.mynewname.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynewname.dto.ChatRequestDto;
import com.mynewname.dto.ChatResponseDto;

import reactor.core.publisher.Mono;

@Service
public class ChatService {

	private final OpenAiService openAiService;

	public ChatService(OpenAiService openAiService) {
		this.openAiService = openAiService;
	}

	public Mono<ChatResponseDto> generateEnglishName(ChatRequestDto requestDto) {
		String prompt = buildPrompt(requestDto);
		return openAiService.getChatCompletionAsync(prompt)
			.map(assistantReply -> {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(assistantReply, ChatResponseDto.class);
				} catch (Exception e) {
					throw new RuntimeException("응답 파싱 실패: " + assistantReply, e);
				}
			});
	}

	private String buildPrompt(ChatRequestDto dto) {
		return """
		당신은 영어 이름 추천 전문가입니다.

		사용자의 정보를 바탕으로 영어 이름을 2~3개 추천해주세요.
		아래 규칙을 반드시 지켜주세요:

		1. 반드시 아래 JSON 구조에 맞춰 출력하세요.
		2. "names" 배열의 이름들은 모두 "reasons" 객체의 키로 존재해야 합니다.
		3. JSON 형식 외의 설명은 절대 포함하지 마세요.
		4. JSON은 마지막까지 **형식적으로 완전한 상태로 마무리**되어야 합니다.
		5. 모든 문자열 값은 쌍따옴표(")로 감싸세요.
		6. "MBTI"는 반드시 정확한 4글자의 MBTI 코드로 작성해주세요. 예: "ENFP", "INTJ", "ISTP" 등. 설명형 문장은 절대 쓰지 마세요.

		다음은 출력 예시 형식입니다:

		{
		  "names": ["이름1", "이름2", "이름3"],
		  "reasons": {
		    "이름1": {
		      "나이/시대적 유행": "...",
		      "직업": "...",
		      "MBTI": "..."
		    },
		    "이름2": {
		      "나이/시대적 유행": "...",
		      "직업": "...",
		      "MBTI": "..."
		    },
		    "이름3": {
		      "나이/시대적 유행": "...",
		      "직업": "...",
		      "MBTI": "..."
		    }
		  }
		}

		반드시 위 JSON 구조 전체를 출력하고, "names" 배열의 이름들은 모두 "reasons"의 키로 존재해야 합니다.
		생략하거나 누락하지 말고 JSON으로만 정확하게 출력하세요.

		사용자 정보:
		- 나이: %s
		- 성별: %s
		1. 직업 또는 희망 직업: %s
		2. 파티 초대 시 반응: %s
		3. 로또 당첨 시 첫 행동: %s
		4. 가보고 싶은 과거 시대: %s
		5. 이름의 SNS 검색 희망 여부: %s
		""".formatted(
			dto.getAge(),
			dto.getGender(),
			dto.getQuestionOne(),
			dto.getQuestionTwo(),
			dto.getQuestionThree(),
			dto.getQuestionFour(),
			dto.getQuestionFive()
		);
	}
}
