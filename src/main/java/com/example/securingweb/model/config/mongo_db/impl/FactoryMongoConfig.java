package com.example.securingweb.model.config.mongo_db.impl;

public class FactoryMongoConfig {

	public static MongoConfigCadastros criarConfigCadastro() {
		return new MongoConfigCadastros();
	}

	public static MongoConfigLeituras criarConfigLeituras() {
		return new MongoConfigLeituras();
	}

}
