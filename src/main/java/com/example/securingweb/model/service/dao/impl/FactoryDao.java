package com.example.securingweb.model.service.dao.impl;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.DicaConsumo;
import com.example.securingweb.model.entity.FiltroRelatorio;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.dao.Dao;

public class FactoryDao {

	private FactoryDao() {
	}

	public static Dao<UsuarioVO, String> criarUsuarioDao() {
		return new UsuarioDao();
	}

	public static Dao<ConsumoVO, IntervaloDatasVO> criarConsumoDao() {
		return new ConsumoDao();
	}

	public static Dao<ConsumoVO, FiltroRelatorio> criarRelatorioDao() {
		return new RelatorioDao();
	}

	public static Dao<DicaConsumo, Integer> criarDicaConsumoDao() {
		return new DicaConsumoDao();
	}
}
