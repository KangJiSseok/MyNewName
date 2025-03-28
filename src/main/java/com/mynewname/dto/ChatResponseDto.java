package com.mynewname.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ChatResponseDto {

	private List<String> names;

	private Map<String, ReasonDetail> reasons;

	private List<Long> namesCount;

	private Long totalCount;

	@Data
	public static class ReasonDetail {
		@JsonProperty("나이/시대적 유행")
		private String ageTrend;

		@JsonProperty("직업")
		private String job;

		@JsonProperty("성격")
		private String personality;
	}
}

