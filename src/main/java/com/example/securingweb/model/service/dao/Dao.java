package com.example.securingweb.model.service.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.example.securingweb.model.config.mongo_db.MongoConfig;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public abstract class Dao<T, Type> {
	
	protected MongoConfig mongoConfig;
	
	protected Query query;
	
	public abstract List<T> get(Type filtro);
	
	public abstract List<T> getAll();
	
	public abstract T save(T t);
	
	public abstract UpdateResult update(T t, Type _id);
	
	public abstract DeleteResult delete(Type _id);

}
