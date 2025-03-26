package com.mynewname.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.mynewname.dto.MilvusInsertRequest;
import com.mynewname.service.MilvusService;

@RestController
@RequestMapping("/milvus")
@RequiredArgsConstructor
public class MilvusController {

	private final MilvusService milvusService;

	// 데이터 삽입
	@PostMapping("/insert")
	public String insert(@RequestBody MilvusInsertRequest request) {
		milvusService.insert(request);
		return "Insert Success!";
	}

	// 데이터 조회 (벡터 검색)
	@GetMapping("/search")
	public List<String> search(@RequestParam List<Float> vector,
		@RequestParam(defaultValue = "3") int topK) {
		return milvusService.search(vector, topK);
	}
}
