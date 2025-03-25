package com.mynewname.service;

import org.springframework.stereotype.Service;

import com.mynewname.dto.ChatRequestDto;
import com.mynewname.dto.ChatResponseDto;

@Service
public class ChatService {

	private final OpenAiService openAiService;

	public ChatService(OpenAiService openAiService) {
		this.openAiService = openAiService;
	}

	public ChatResponseDto generateEnglishName(ChatRequestDto requestDto) {
		// 1) Prompt 만들기
		//    실제로는 더 다양하고 상세하게 작성하실 수 있습니다.
		String prompt = buildPrompt(requestDto);

		// 2) ChatGPT API 호출
		String assistantReply = openAiService.getChatCompletion(prompt);

		// 3) ChatGPT 응답을 가공해서 name/explanation 필드에 매핑
		//    여기서는 파싱을 위한 규칙(예: JSON 형식으로 응답)을 미리 정해두고
		//    ChatGPT가 JSON 형식으로 응답하도록 prompt를 구성하는 편이 안정적입니다.

		// 단순 예시: "name: Ethan\nexplanation: ~~~~" 형태로 나온다고 가정
		String name = extractName(assistantReply);
		String explanation = extractExplanation(assistantReply);

		return new ChatResponseDto(name, explanation);
	}

	private String buildPrompt(ChatRequestDto dto) {
		// 실제 프롬프트 예시
		// ChatGPT에게 JSON 형태로 결과를 달라고 요청하면 파싱하기 편리합니다.

		return """
               아래의 정보를 바탕으로 어울리는 영어 이름을 추천해줘.
               형식은 반드시 JSON으로 주고, 예시는 다음과 같은 형태여야 해:
               {
                 "name": "~~~",
                 "explanation": "~~~"
               }

               [정보]
               나이: %d
               성별: %s
               MBTI: %s
               직업(혹은 꿈꾸는 직업): %s
               나만의 독특한 점: %s
               
               가장 잘 어울리는 영어 이름 1개를 추천해주고,
               그 이유를 JSON에서 explanation 키로 짧게 적어줘.
               """.formatted(
			dto.getAge(),
			dto.getGender(),
			dto.getMbti(),
			dto.getJob(),
			dto.getUniqueness()
		);
	}

	private String extractName(String assistantReply) {
		// 본격적으로는 JSON parse 수행 (Jackson, Gson 등)
		// 단순히 예시로, "name": "???"
		// 정규식 or substring으로 파싱 가능
		// 여기선 매우 단순화된 예시
		String name = "Unknown";
		int nameIndex = assistantReply.indexOf("\"name\":");
		if (nameIndex != -1) {
			int start = assistantReply.indexOf("\"", nameIndex + 7) + 1;
			int end = assistantReply.indexOf("\"", start);
			if (start != -1 && end != -1) {
				name = assistantReply.substring(start, end);
			}
		}
		return name;
	}

	private String extractExplanation(String assistantReply) {
		// 간단히 explanation 추출
		String explanation = "";
		int expIndex = assistantReply.indexOf("\"explanation\":");
		if (expIndex != -1) {
			int start = assistantReply.indexOf("\"", expIndex + 14) + 1;
			int end = assistantReply.indexOf("\"", start);
			if (start != -1 && end != -1) {
				explanation = assistantReply.substring(start, end);
			}
		}
		return explanation;
	}
}