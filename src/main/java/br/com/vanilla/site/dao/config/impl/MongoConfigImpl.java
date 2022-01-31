package br.com.vanilla.site.dao.config.impl;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import br.com.vanilla.site.dao.config.MongoConfig;

public class MongoConfigImpl extends AbstractMongoClientConfiguration implements MongoConfig {

	protected MongoConfigImpl() {
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
