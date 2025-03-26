package com.mynewname.config;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MilvusConfig {

	@Bean
	public MilvusServiceClient milvusClient() {
		ConnectParam connectParam = ConnectParam.newBuilder()
			.withHost("localhost")    // 또는 milvus-standalone 컨테이너가 돌고있는 호스트 IP
			.withPort(19530)          // Milvus 기본 포트
			.build();

		return new MilvusServiceClient(connectParam);
	}

	public void createCollection(MilvusServiceClient milvusClient) {
		// 1) PK 필드 정의
		FieldType pkField = FieldType.newBuilder()
			.withName("id")
			.withDataType(DataType.Int64)
			.withPrimaryKey(true)
			.withAutoID(true)
			.build();

		// 2) 벡터 필드 정의
		FieldType vectorField = FieldType.newBuilder()
			.withName("embed")
			.withDataType(DataType.FloatVector)
			.withDimension(128)  // 차원 설정
			.build();

		// 3) 나머지 문자열 필드 (선택)
		FieldType titleField = FieldType.newBuilder()
			.withName("title")
			.withDataType(DataType.VarChar)
			.withMaxLength(512)     // VarChar는 길이를 지정
			.build();

		// 4) Collection 스키마 생성
		CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
			.withCollectionName("test_collection")
			.withDescription("테스트용 컬렉션")
			.addFieldType(pkField)
			.addFieldType(vectorField)
			.addFieldType(titleField)
			.build();

		// 5) Collection 생성 요청
		R<RpcStatus> response = milvusClient.createCollection(createCollectionParam);
		if (response.getStatus() == R.Status.Success.getCode()) {
			System.out.println("Collection created successfully!");
		} else {
			System.out.println("Collection creation failed: " + response.getMessage());
		}
	}
}
