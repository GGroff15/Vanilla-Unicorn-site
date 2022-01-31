package br.com.vanilla.site.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.com.vanilla.site.dao.Dao;
import br.com.vanilla.site.dao.config.MongoConfig;
import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.model.constants.Constants;

@Service
public class UsuarioDao extends Dao<Usuario, String> {

	protected UsuarioDao(MongoConfig mongoConfig) {
		super.mongoConfig = mongoConfig;
	}

	@Override
	public List<Usuario> get(String filtro) {
		query = new Query(Criteria.where(Constants.USUARIO_USERNAME).is(filtro));
		return mongoConfig.mongoTemplate().find(query, Usuario.class);
	}

	@Override
	public List<Usuario> getAll() {
		return mongoConfig.mongoTemplate().findAll(Usuario.class);
	}

	@Override
	public Usuario save(Usuario dados) {
		return mongoConfig.mongoTemplate().save(dados);
	}

	@Override
	public UpdateResult update(Usuario dado, String username) {
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

		return mongoConfig.mongoTemplate().updateFirst(query, update, Usuario.class);
	}

	@Override
	public DeleteResult delete(String id) {
		query = new Query(Criteria.where(Constants.ID).is(id));
		return mongoConfig.mongoTemplate().remove(query, "clientes");
	}

}
