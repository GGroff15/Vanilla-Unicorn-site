package br.com.vanilla.site.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;

@Configuration
public interface MongoConfig {

	@Bean
	public MongoClient client();

	public MongoTemplate mongoTemplate();
}
