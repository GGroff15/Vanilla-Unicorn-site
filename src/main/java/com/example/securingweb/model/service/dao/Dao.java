package com.example.securingweb.model.service.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.example.securingweb.model.config.mongo_db.MongoConfig;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public abstract class Dao<T, M> {

	protected MongoConfig mongoConfig;

	protected Query query;

	public abstract List<T> get(M filtro);

	public abstract List<T> getAll();

	public abstract T save(T t);

	public abstract UpdateResult update(T t, M _id);

	public abstract DeleteResult delete(M _id);

}
