package com.utn.tacs.tit4tat.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.utn.tacs.tit4tat.dao.GenericDao;

public abstract class GenericDaoImpl<T extends Serializable , ID extends Serializable> implements GenericDao<T, ID> {

	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * getPersistentClass
	 * 
	 * @return Class<T>
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T loadById(ID id, boolean lock) {
		return null;
	}

	public List<T> findAll() {
		return null;
	}

	public T getById(ID id) {
		return null;
	}
	
	
//	@Transactional(readOnly = false)
	public ID save(T entity) {
		return (ID) null;
	}
	

//	@Transactional(readOnly = false)
	public void saveOrUpdate(T entity) {
	}

//	@Transactional(readOnly = false)
	public void delete(T entity) {
	}

	public void flush() {
	}

	public void clear() {
	}
	
	public void saveAll(List<T> entities){
	}
}
