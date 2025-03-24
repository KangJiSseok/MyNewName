package com.mynewname.init;

import org.springframework.stereotype.Component;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.HasCollectionParam;
import jakarta.annotation.PostConstruct;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;


@Component
public class MilvusCollectionInitializer {

	private final MilvusServiceClient milvusClient;
	private static final String COLLECTION_NAME = "test_collection";

	public MilvusCollectionInitializer(MilvusServiceClient milvusClient) {
		this.milvusClient = milvusClient;
	}

	@PostConstruct
	public void init() {
		createCollection();
		createIndex(); // ✅ 인덱스를 생성한 뒤
		loadCollection(); // ✅ 메모리에 컬렉션을 올림
	}

	private void createCollection() {
		R<Boolean> hasCollectionResponse = milvusClient.hasCollection(
			HasCollectionParam.newBuilder()
				.withCollectionName(COLLECTION_NAME)
				.build()
		);

		if (Boolean.TRUE.equals(hasCollectionResponse.getData())) {
			System.out.println("Collection already exists: " + COLLECTION_NAME);
			return;
		}

		FieldType pkField = FieldType.newBuilder()
			.withName("id")
			.withDataType(DataType.Int64)
			.withPrimaryKey(true)
			.withAutoID(true)
			.build();

		FieldType vectorField = FieldType.newBuilder()
			.withName("embed")
			.withDataType(DataType.FloatVector)
			.withDimension(128)
			.build();

		FieldType titleField = FieldType.newBuilder()
			.withName("title")
			.withDataType(DataType.VarChar)
			.withMaxLength(512)
			.build();

		CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
			.withCollectionName(COLLECTION_NAME)
			.withDescription("테스트용 컬렉션")
			.addFieldType(pkField)
			.addFieldType(vectorField)
			.addFieldType(titleField)
			.build();

		R<RpcStatus> response = milvusClient.createCollection(createCollectionParam);

		if (response.getStatus() == R.Status.Success.getCode()) {
			System.out.println("Collection created successfully!");
		} else {
			throw new RuntimeException("Collection creation failed: " + response.getMessage());
		}
	}

	// ✅ 인덱스 생성
	private void createIndex() {
		R<RpcStatus> response = milvusClient.createIndex(
			io.milvus.param.index.CreateIndexParam.newBuilder()
				.withCollectionName(COLLECTION_NAME)
				.withFieldName("embed") // 벡터 필드 이름
				.withIndexType(IndexType.IVF_FLAT) // IVF_FLAT, HNSW, AUTOINDEX 등
				.withMetricType(MetricType.L2) // 검색 기준 거리
				.withExtraParam("{\"nlist\":128}") // IVF 인덱스일 경우 nlist 설정 필요
				.withSyncMode(Boolean.TRUE) // 동기 실행: 인덱스 생성 완료까지 대기
				.build()
		);

		if (response.getStatus() == R.Status.Success.getCode()) {
			System.out.println("Index created successfully!");
		} else {
			throw new RuntimeException("Index creation failed: " + response.getMessage());
		}
	}

	// ✅ 컬렉션을 메모리에 로드
	private void loadCollection() {
		R<RpcStatus> response = milvusClient.loadCollection(
			io.milvus.param.collection.LoadCollectionParam.newBuilder()
				.withCollectionName(COLLECTION_NAME)
				.build()
		);

		if (response.getStatus() == R.Status.Success.getCode()) {
			System.out.println("Collection loaded successfully!");
		} else {
			throw new RuntimeException("Collection load failed: " + response.getMessage());
		}
	}
}
