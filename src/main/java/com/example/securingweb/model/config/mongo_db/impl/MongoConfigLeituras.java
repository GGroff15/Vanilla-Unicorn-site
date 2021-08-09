package com.example.securingweb.model.config.mongo_db.impl;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.securingweb.model.config.mongo_db.MongoConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoConfigLeituras extends AbstractMongoClientConfiguration implements MongoConfig {

	protected MongoConfigLeituras() {
	}

	@Override
	public MongoClient client() {
		return MongoClients.create("mongodb+srv://" + System.getenv("mongo_user") + ":" + System.getenv("mongo_pass")
				+ "@monitor.2jwej.mongodb.net/leituras?retryWrites=true&w=majority");
	}

	@Override
	public String getDatabaseName() {
		return "leituras";
	}

	@Override
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(client(), "leituras");
	}

}
