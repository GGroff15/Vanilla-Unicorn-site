/**
 * 
 */
package br.com.vanilla.site.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.com.vanilla.site.dao.Dao;
import br.com.vanilla.site.dao.config.MongoConfig;
import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.entity.IntervaloDatasVO;
import br.com.vanilla.site.model.constants.Constants;
import br.com.vanilla.site.model.enums.PropriedadeUsuarioEnum;
import br.com.vanilla.site.utils.UsuarioUtils;

/**
 * @author guilh
 *
 */
public class ConsumoDao extends Dao<ConsumoVO, IntervaloDatasVO> {

	protected ConsumoDao(MongoConfig mongoConfig) {
		super.mongoConfig = mongoConfig;
	}

	@Override
	public List<ConsumoVO> get(IntervaloDatasVO filtro) {
		query = new Query(
				Criteria.where(Constants.CONSUMO_DATA).lte(filtro.getDataFinal()).gte(filtro.getDataInicial()));

		String username = UsuarioUtils.recuperarDadoUsuario(PropriedadeUsuarioEnum.USERNAME.getDescricao());

		return mongoConfig.mongoTemplate().find(query, ConsumoVO.class, username);
	}

	@Override
	public List<ConsumoVO> getAll() {
		return mongoConfig.mongoTemplate().findAll(ConsumoVO.class,
				UsuarioUtils.recuperarDadoUsuario(PropriedadeUsuarioEnum.USERNAME.getDescricao()));
	}

	@Override
	public ConsumoVO save(ConsumoVO consumo) {
		return mongoConfig.mongoTemplate().save(consumo, "AndreVanilla");
	}

	@Override
	public DeleteResult delete(IntervaloDatasVO _id) {
		throw new UnsupportedOperationException("Metodo nao implantado");
	}

	@Override
	public UpdateResult update(ConsumoVO t, IntervaloDatasVO _id) {
		throw new UnsupportedOperationException("Metodo nao implantado");
	}

}
