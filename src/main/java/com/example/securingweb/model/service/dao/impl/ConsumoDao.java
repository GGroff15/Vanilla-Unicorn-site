/**
 * 
 */
package com.example.securingweb.model.service.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.securingweb.model.config.mongo_db.impl.FactoryMongoConfig;
import com.example.securingweb.model.constants.Constants;
import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.enums.PropriedadeUsuarioEnum;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.utils.UsuarioUtils;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * @author guilh
 *
 */
public class ConsumoDao extends Dao<ConsumoVO, IntervaloDatasVO> {

	protected ConsumoDao() {
	}

	@Override
	public List<ConsumoVO> get(IntervaloDatasVO filtro) {
		mongoConfig = FactoryMongoConfig.criarConfigLeituras();
		query = new Query(
				Criteria.where(Constants.CONSUMO_DATA).lte(filtro.getDataFinal()).gte(filtro.getDataInicial()));

		String username = UsuarioUtils.recuperarDadoUsuario(PropriedadeUsuarioEnum.USERNAME.getDescricao());

		return mongoConfig.mongoTemplate().find(query, ConsumoVO.class, username);
	}

	@Override
	public List<ConsumoVO> getAll() {
		mongoConfig = FactoryMongoConfig.criarConfigLeituras();
		return mongoConfig.mongoTemplate().findAll(ConsumoVO.class,
				UsuarioUtils.recuperarDadoUsuario(PropriedadeUsuarioEnum.USERNAME.getDescricao()).toString());
	}

	@Override
	public ConsumoVO save(ConsumoVO consumo) {
		mongoConfig = FactoryMongoConfig.criarConfigLeituras();
		return mongoConfig.mongoTemplate().save(consumo, "AndreVanilla");
	}

	@Override
	public DeleteResult delete(IntervaloDatasVO _id) {
		return null; // nao aplicavel
	}

	@Override
	public UpdateResult update(ConsumoVO t, IntervaloDatasVO _id) {
		return null; // nao aplicavel
	}

}
