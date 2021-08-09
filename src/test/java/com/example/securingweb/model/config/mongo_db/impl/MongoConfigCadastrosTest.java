package com.example.securingweb.model.config.mongo_db.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.example.securingweb.model.config.mongo_db.MongoConfig;

class MongoConfigCadastrosTest {
	@Test
	void testClient() {
		MongoConfig config = FactoryMongoConfig.criarConfigCadastro();
		assertNotNull(config.client());
	}

	@Test
	void testGetDatabaseName() {
		MongoConfig config = FactoryMongoConfig.criarConfigCadastro();
		assertEquals("cadastros", config.getDatabaseName());
	}

	@Test
	void testTemplate() {
		MongoConfig config = FactoryMongoConfig.criarConfigCadastro();
		assertNotNull(config.mongoTemplate());
	}
}
