// package com.mynewname.repository;
//
// import io.milvus.client.MilvusServiceClient;
// import io.milvus.grpc.SearchResults;
// import io.milvus.param.MetricType;
// import io.milvus.param.R;
// import io.milvus.param.dml.InsertParam;
// import io.milvus.param.dml.SearchParam;
// import io.milvus.response.SearchResultsWrapper;
// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Repository;
//
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
//
// import com.mynewname.dto.MilvusInsertRequest;
//
// @Repository
// @RequiredArgsConstructor
// public class MilvusRepository {
//
// 	private final MilvusServiceClient milvusClient;
//
// 	private final String COLLECTION_NAME = "test_collection";
//
// 	@PostConstruct
// 	public void loadCollection() {
// 		// 컬렉션을 메모리에 로드 (성능 최적화)
// 		milvusClient.loadCollection(
// 			io.milvus.param.collection.LoadCollectionParam.newBuilder()
// 				.withCollectionName(COLLECTION_NAME)
// 				.build()
// 		);
// 		System.out.println("Collection loaded in memory: " + COLLECTION_NAME);
// 	}
//
// 	public void insert(MilvusInsertRequest request) {
// 		// Milvus에 insert할 데이터를 리스트로 구성
// 		List<Long> idList = Collections.singletonList(0L); // AutoID=true면 없어도 되지만 넣어줌
// 		List<List<Float>> vectorList = Collections.singletonList(request.getEmbed());
// 		List<String> titleList = Collections.singletonList(request.getTitle());
//
// 		// 필드 이름과 데이터 매핑
// 		List<InsertParam.Field> fields = new ArrayList<>();
// 		fields.add(new InsertParam.Field("embed", vectorList));
// 		fields.add(new InsertParam.Field("title", titleList));
//
// 		// Insert 실행
// 		InsertParam insertParam = InsertParam.newBuilder()
// 			.withCollectionName(COLLECTION_NAME)
// 			.withFields(fields)
// 			.build();
//
// 		R<io.milvus.grpc.MutationResult> response = milvusClient.insert(insertParam);
// 		if (response.getStatus() != R.Status.Success.getCode()) {
// 			throw new RuntimeException("Insert failed: " + response.getMessage());
// 		}
//
// 		System.out.println("Insert success!");
// 	}
//
// 	public List<String> search(List<Float> vector, int topK) {
//
// 		milvusClient.loadCollection(
// 			io.milvus.param.collection.LoadCollectionParam.newBuilder()
// 				.withCollectionName(COLLECTION_NAME)
// 				.build()
// 		);
// 		System.out.println("Collection loaded: " + COLLECTION_NAME);
//
// 		List<List<Float>> searchVectors = Collections.singletonList(vector);
//
// 		SearchParam searchParam = SearchParam.newBuilder()
// 			.withCollectionName(COLLECTION_NAME)
// 			.withMetricType(MetricType.L2) // ← enum 타입!
// 			.withOutFields(List.of("title"))
// 			.withVectors(searchVectors)
// 			.withTopK(topK)
// 			.withVectorFieldName("embed")
// 			.withParams("{\"nprobe\":10}")
// 			.build();
//
// 		R<SearchResults> response = milvusClient.search(searchParam);
//
// 		if (response.getStatus() != R.Status.Success.getCode()) {
// 			throw new RuntimeException("Search failed: " + response.getMessage());
// 		}
//
// 		// 결과 파싱
// 		SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
// 		List<SearchResultsWrapper.IDScore> idScores = wrapper.getIDScore(0);
//
// 		List<String> titles = new ArrayList<>();
// 		List<?> fieldsData = wrapper.getFieldData("title", 0);
//
// 		for (int i = 0; i < idScores.size(); i++) {
// 			SearchResultsWrapper.IDScore score = idScores.get(i);
// 			System.out.println("ID: " + score.getLongID() + ", Score: " + score.getScore());
//
// 			if (fieldsData != null && i < fieldsData.size()) {
// 				String title = (String) fieldsData.get(i);
// 				titles.add(title);
// 				System.out.println("Title: " + title);
// 			}
// 		}
//
// 		return titles;
// 	}
//
//
// }
