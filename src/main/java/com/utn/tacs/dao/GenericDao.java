package com.utn.tacs.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<T  extends Serializable, ID extends Serializable> {

	/**
	 * loadById
	 * 
	 * 
	 * @param id
	 * @param lock
	 * @return T
	 */
	public T loadById(ID id, boolean lock);
	
	/**
	 * getById
	 * 
	 * 
	 * @param id
	 * @return T
	 */
	public T getById(ID id);
	

	/**
	 * findAll
	 * 
	 * 
	 * @return List<T>
	 */
	public List<T> findAll();

	/**
	 * save
	 * 
	 * 
	 * @param entity void
	 */
	public ID save(T entity);

	/**
	 * delete
	 * 
	 * 
	 * @param entity void
	 */
	public void delete(T entity);

	
	/**
	 * flush
	 *  
	 */
	public void flush();



	/**
	 * clear
	 *  
	 */
	public void clear();
	
	
	public void saveAll(List<T> entities);
	
	
	public void saveOrUpdate(T entity);

}
