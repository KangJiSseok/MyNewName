package com.mynewname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
	org.springframework.ai.autoconfigure.vectorstore.milvus.MilvusVectorStoreAutoConfiguration.class
})
public class MyNewNameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyNewNameApplication.class, args);
	}

}
