package com.example.securingweb.model.config.mongo_db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;

@Configuration
public interface MongoConfig {
	
	@Bean
	public MongoClient client();
	
	public String getDatabaseName();
	
	public MongoTemplate mongoTemplate();
}
