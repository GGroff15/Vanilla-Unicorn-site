package com.example.securingweb.model.config.mongo_db.impl;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.securingweb.model.config.mongo_db.MongoConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoConfigCadastros extends AbstractMongoClientConfiguration implements MongoConfig {

	protected MongoConfigCadastros() {
	}

	@Override
	public MongoClient client() {
		return MongoClients.create("mongodb+srv://" + System.getenv("mongo_user") + ":" + System.getenv("mongo_pass")
				+ "@monitor.2jwej.mongodb.net/cadastros?retryWrites=true&w=majority");
	}

	@Override
	public String getDatabaseName() {
		return "cadastros";
	}

	@Override
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(client(), "cadastros");
	}

}
