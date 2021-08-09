package com.example.securingweb.model.service.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.securingweb.model.config.mongo_db.impl.FactoryMongoConfig;
import com.example.securingweb.model.constants.Constants;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Service
public class UsuarioDao extends Dao<UsuarioVO, String> {

	protected UsuarioDao() {
	}

	@Override
	public List<UsuarioVO> get(String filtro) {
		mongoConfig = FactoryMongoConfig.criarConfigCadastro();
		query = new Query(Criteria.where(Constants.USUARIO_USERNAME).is(filtro));
		return mongoConfig.mongoTemplate().find(query, UsuarioVO.class);
	}

	@Override
	public List<UsuarioVO> getAll() {
		mongoConfig = FactoryMongoConfig.criarConfigCadastro();
		return mongoConfig.mongoTemplate().findAll(UsuarioVO.class);
	}

	@Override
	public UsuarioVO save(UsuarioVO dados) {
		mongoConfig = FactoryMongoConfig.criarConfigCadastro();
		return mongoConfig.mongoTemplate().save(dados);
	}

	@Override
	public UpdateResult update(UsuarioVO dado, String username) {
		mongoConfig = FactoryMongoConfig.criarConfigCadastro();
		query = new Query(Criteria.where(Constants.USUARIO_USERNAME).is(username));
		Update update = new Update();

		update.set(Constants.USUARIO_NOME, dado.getNome());
		update.set(Constants.USUARIO_USERNAME, dado.getUsername());
		update.set(Constants.USUARIO_SENHA, dado.getSenha());
		update.set(Constants.USUARIO_EMAIL, dado.getEmail());
		update.set(Constants.USUARIO_POTENCIA, dado.getPotencia());
		update.set(Constants.USUARIO_CELULAR, dado.getCelular());
		update.set(Constants.USUARIO_NOTIFICACAO_CELULAR, dado.isNotificacaoCelular());
		update.set(Constants.USUARIO_NOTIFICACAO_EMAIL, dado.isNotificacaoEmail());
		update.set(Constants.USUARIO_COMPARAR, dado.isComparar());
		update.set(Constants.USUARIO_META, dado.getMeta());

		return mongoConfig.mongoTemplate().updateFirst(query, update, UsuarioVO.class);
	}

	@Override
	public DeleteResult delete(String _id) {
		mongoConfig = FactoryMongoConfig.criarConfigCadastro();
		query = new Query(Criteria.where(Constants.ID).is(_id));
		return mongoConfig.mongoTemplate().remove(query, "clientes");
	}

}
