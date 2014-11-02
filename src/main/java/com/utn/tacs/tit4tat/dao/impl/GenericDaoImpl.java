package com.utn.tacs.tit4tat.dao.impl;

import static com.utn.tacs.tit4tat.objectify.OfyService.ofy;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.utn.tacs.tit4tat.dao.GenericDao;
import com.utn.tacs.tit4tat.objectify.Identifiable;

public abstract class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

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

	public List<T> findAll() {
		List<T> all = (List<T>) ofy().load().type(this.getPersistentClass()).list();
		return all;
	}

	public T getById(ID id) {
		return ofy().load().type(this.getPersistentClass()).id((Long)id).now();
	}
	
	
//	@Transactional(readOnly = false)
	@SuppressWarnings("unchecked")
	public ID save(T entity) {
		ofy().save().entity(entity);
		return (ID) ((Identifiable) entity).getId();
	}
	

//	@Transactional(readOnly = false)
	public void saveOrUpdate(T entity) {
		ofy().save().entity(entity);
	}

	public void saveAll(List<T> entities){
		ofy().save().entities(entities);
	}
	
//	@Transactional(readOnly = false)
	public void delete(T entity) {
		@SuppressWarnings("unchecked")
		ID id = (ID) ((Identifiable) entity).getId();
		ofy().delete().type(this.getPersistentClass()).id((Long) id);
	}

	public void flush() {
		throw new RuntimeException("Metodo no implementado.");
	}

	public void clear() {
		throw new RuntimeException("Metodo no implementado.");
	}
	
	public T loadById(ID id, boolean lock) {
		throw new RuntimeException("Metodo no implementado.");		
	}
}
