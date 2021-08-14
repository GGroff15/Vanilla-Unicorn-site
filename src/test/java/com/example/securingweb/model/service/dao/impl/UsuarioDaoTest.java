package com.example.securingweb.model.service.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

class UsuarioDaoTest {

	@Test
	void testGet() {
		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();
		List<UsuarioVO> retorno = dao.get("AndreVanilla");
		assertEquals(1, retorno.size());
		assertNotEquals(null, retorno.get(0));
	}

	@Test
	void testGetAll() {
		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();
		List<UsuarioVO> retorno = dao.getAll();
		assertNotEquals(0, retorno.size());
	}

	@Test
	void testSave() {
		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();

		UsuarioVO usuario = new UsuarioVO();
		usuario.setId(new ObjectId("60d278a37a894d07dd2e5962"));
		usuario.setNome("João");
		usuario.setUsername("joao");
		usuario.setEmail("joao@domain.com");
		usuario.setSenha(new BCryptPasswordEncoder().encode("123"));
		usuario.setNotificacaoEmail(false);
		UsuarioVO retorno = dao.save(usuario);
		assertNotEquals(null, retorno.getId());
	}

	@Test
	void testUpdate() {
		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();

		UsuarioVO usuario = new UsuarioVO();
		usuario.setId(new ObjectId("616661736661736466617366"));
		usuario.setNome("João");
		usuario.setUsername("joao");
		usuario.setEmail("joao@domain.com");
		usuario.setSenha(new BCryptPasswordEncoder().encode("123"));
		usuario.setNotificacaoEmail(false);
		dao.save(usuario);

		Random random = new Random();
		usuario.setNome("João2");
		usuario.setEmail("joao@domain.com");
		usuario.setSenha(new BCryptPasswordEncoder().encode("616661736661736466617366"));
		usuario.setPotencia(random.nextLong());
		usuario.setNotificacaoEmail(false);
		UpdateResult resultado = dao.update(usuario, "joao");
		assertEquals(1, resultado.getModifiedCount());
	}

	@Test
	void testDelete() {
		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();
		DeleteResult resultado = dao.delete("60d278a37a894d07dd2e5962");
		assertEquals(1, resultado.getDeletedCount());
	}
}
