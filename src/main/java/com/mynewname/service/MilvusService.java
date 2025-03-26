// package com.mynewname.service;
//
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
// import com.mynewname.dto.MilvusInsertRequest;
// import com.mynewname.repository.MilvusRepository;
//
// @Service
// @RequiredArgsConstructor
// public class MilvusService {
//
// 	private final MilvusRepository milvusRepository;
//
// 	public void insert(MilvusInsertRequest request) {
// 		milvusRepository.insert(request);
// 	}
//
// 	public List<String> search(List<Float> vector, int topK) {
// 		return milvusRepository.search(vector, topK);
// 	}
// }
