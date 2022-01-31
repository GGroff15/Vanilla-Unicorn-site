package br.com.vanilla.site.dao.config.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import br.com.vanilla.site.dao.config.MongoConfig;

@Configuration
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
