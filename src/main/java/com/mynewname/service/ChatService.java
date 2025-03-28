package com.mynewname.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynewname.dto.ChatRequestDto;
import com.mynewname.dto.ChatResponseDto;
import com.mynewname.entity.Name;
import com.mynewname.repository.NameRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final OpenAiService openAiService;
	private final NameRepository nameRepository;
	private final ObjectMapper objectMapper;

	public Mono<ChatResponseDto> generateEnglishName(ChatRequestDto requestDto) {
		String prompt = buildPrompt(requestDto);

		return openAiService.getChatCompletionAsync(prompt)
			.map(assistantReply -> {
				try {
					ChatResponseDto responseDto = objectMapper.readValue(assistantReply, ChatResponseDto.class);

					// 이름 저장 및 count 증가
					saveOrUpdateNames(responseDto.getNames());

					// 각 이름별 추천된 횟수 조회
					List<Long> nameCounts = responseDto.getNames().stream()
						.map(name -> nameRepository.findByName(name)
							.map(Name::getCount)
							.orElse(0L))
						.toList();
					responseDto.setNamesCount(nameCounts);

					// 전체 이름 총 개수
					responseDto.setTotalCount(nameRepository.count());

					return responseDto;

				} catch (Exception e) {
					throw new RuntimeException("응답 파싱 실패: " + assistantReply, e);
				}
			});

	}

	private String buildPrompt(ChatRequestDto dto) {
		return """
        당신은 영어 이름 추천 전문가입니다.

        아래 사용자 정보를 바탕으로 성격을 유추한 후, 해당 성격/직업/나이/성별에 어울리는 영어 이름 3개를 추천하세요.

        반드시 아래 JSON 형식 **그대로만** 출력하세요.

        {
          "names": ["이름1", "이름2", "이름3"],
          "reasons": {
            "이름1": {
              "나이/시대적 유행": "이유 작성",
              "직업": "이유 작성",
              "성격": "이유 작성"
            },
            "이름2": {
              "나이/시대적 유행": "이유 작성",
              "직업": "이유 작성",
              "성격": "이유 작성"
            },
            "이름3": {
              "나이/시대적 유행": "이유 작성",
              "직업": "이유 작성",
              "성격": "이유 작성"
            }
          }
        }

        반드시 위 JSON 외에는 아무것도 출력하지 마세요.
        사용자 정보:
        - 나이: %s
        - 성별: %s
        1. 직업 또는 희망 직업: %s
        2. 친구가 갑자기 파티에 초대할 때 반응: %s
        3. 로또 당첨 시 첫 행동: %s
        4. 가보고 싶은 과거 시대: %s
        5. 이름의 흔함 정도: %s
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


	private void saveOrUpdateNames(List<String> names) {
		for (String name : names) {
			nameRepository.findByName(name)
				.ifPresentOrElse(
					existing -> {
						existing.increaseCount();
						nameRepository.save(existing);
					},
					() -> {
						Name newName = Name.builder()
							.name(name)
							.build();
						nameRepository.save(newName);
					}
				);
		}
	}
}
