package br.com.vanilla.site.dao.impl;

import br.com.vanilla.site.dao.Dao;
import br.com.vanilla.site.dao.config.MongoConfig;
import br.com.vanilla.site.dao.config.impl.FactoryMongoConfig;
import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.entity.IntervaloDatasVO;
import br.com.vanilla.site.entity.Usuario;

public class FactoryDao {

	private FactoryDao() {
	}

	public static Dao<Usuario, String> criarUsuarioDao() {
		MongoConfig configCadastro = FactoryMongoConfig.criarConfigCadastro();
		return new UsuarioDao(configCadastro);
	}

	public static Dao<ConsumoVO, IntervaloDatasVO> criarConsumoDao() {
		MongoConfig configLeituras = FactoryMongoConfig.criarConfigLeituras();
		return new ConsumoDao(configLeituras);
	}

}
