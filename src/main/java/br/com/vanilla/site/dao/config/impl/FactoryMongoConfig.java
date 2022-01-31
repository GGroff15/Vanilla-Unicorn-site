package br.com.vanilla.site.dao.config.impl;

public class FactoryMongoConfig {

	private FactoryMongoConfig() {
	}

	public static MongoConfigCadastros criarConfigCadastro() {
		return new MongoConfigCadastros();
	}

	public static MongoConfigImpl criarConfigLeituras() {
		return new MongoConfigImpl();
	}

}
