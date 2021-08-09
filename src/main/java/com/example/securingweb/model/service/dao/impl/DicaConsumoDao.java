package com.example.securingweb.model.service.dao.impl;

import java.util.Collections;
import java.util.List;

import com.example.securingweb.model.config.mongo_db.impl.FactoryMongoConfig;
import com.example.securingweb.model.entity.DicaConsumo;
import com.example.securingweb.model.service.dao.Dao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class DicaConsumoDao extends Dao<DicaConsumo, Integer> {

	protected DicaConsumoDao() {
	}

	@Override
	public List<DicaConsumo> get(Integer filtro) {
		return Collections.emptyList();
	}

	@Override
	public List<DicaConsumo> getAll() {
		mongoConfig = FactoryMongoConfig.criarConfigCadastro();
		return mongoConfig.mongoTemplate().findAll(DicaConsumo.class, "dicas economia");
	}

	@Override
	public DicaConsumo save(DicaConsumo t) {
		return new DicaConsumo();
	}

	@Override
	public UpdateResult update(DicaConsumo t, Integer _id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DeleteResult delete(Integer _id) {
		throw new UnsupportedOperationException();
	}

}
