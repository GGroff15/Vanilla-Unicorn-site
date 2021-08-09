package com.example.securingweb.model.config.mongo_db.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.example.securingweb.model.config.mongo_db.MongoConfig;

class MongoConfigLeiturasTest {
	@Test
	void testClient() {
		MongoConfig config = FactoryMongoConfig.criarConfigLeituras();
		assertNotNull(config.client());
	}

	@Test
	void testGetDatabaseName() {
		MongoConfig config = FactoryMongoConfig.criarConfigLeituras();
		assertEquals("leituras", config.getDatabaseName());
	}

	@Test
	void testTemplate() {
		MongoConfig config = FactoryMongoConfig.criarConfigLeituras();
		assertNotNull(config.mongoTemplate());
	}
}
