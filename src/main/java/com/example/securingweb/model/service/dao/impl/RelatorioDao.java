/**
 * 
 */
package com.example.securingweb.model.service.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.securingweb.model.config.mongo_db.impl.FactoryMongoConfig;
import com.example.securingweb.model.constants.Constants;
import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.FiltroRelatorio;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.service.dao.Dao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * @author guilh
 *
 */
public class RelatorioDao extends Dao<ConsumoVO, FiltroRelatorio> {

	protected RelatorioDao() {
	}

	@Override
	public List<ConsumoVO> get(FiltroRelatorio filtro) {

		IntervaloDatasVO intervalo = filtro.getIntervaloDatasVO();

		mongoConfig = FactoryMongoConfig.criarConfigLeituras();
		query = new Query(
				Criteria.where(Constants.CONSUMO_DATA).lte(intervalo.getDataFinal()).gte(intervalo.getDataInicial()));

		return mongoConfig.mongoTemplate().find(query, ConsumoVO.class, filtro.getUsername());
	}

	@Override
	public List<ConsumoVO> getAll() {
		return Collections.emptyList();
	}

	@Override
	public ConsumoVO save(ConsumoVO consumo) {
		mongoConfig = FactoryMongoConfig.criarConfigLeituras();
		return mongoConfig.mongoTemplate().save(consumo, "AndreVanilla");
	}

	@Override
	public DeleteResult delete(FiltroRelatorio _id) {
		return null; // nao aplicavel
	}

	@Override
	public UpdateResult update(ConsumoVO t, FiltroRelatorio _id) {
		return null; // nao aplicavel
	}

}
