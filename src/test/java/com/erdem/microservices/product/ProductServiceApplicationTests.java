package com.erdem.microservices.product;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // whenever we run the test, it will start the server on a random port
class ProductServiceApplicationTests {
	//initialize mongodb test container 
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
	
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
		System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl()); // set the mongodb uri to the test container uri
	}

	@Test
	void shouldCreateProduct() {
		String requestBody= """
				{
					"name": "Samsung Galaxy",
					"description": "brand new",
					"price": 700
				}
				""";
		RestAssured.given()
			.contentType("application/json")
			.body(requestBody)
			.when()
			.post("/api/product")
			.then()
			.body("id", notNullValue())
			.body("name", Matchers.equalTo("Samsung Galaxy"))
			.body("description", Matchers.equalTo("brand new"))
			.body("price", Matchers.equalTo(700));
		}
}
