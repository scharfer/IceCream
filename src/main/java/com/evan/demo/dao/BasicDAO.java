package com.evan.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

/**
 * A basic DAO implementation that contains normal DAO functions
 * @author escharfer
 *
 * @param <E>
 * @param <T>
 */
@Transactional
public abstract class BasicDAO<E, T> implements DAO<E,T> {
	
	@PersistenceContext
    protected EntityManager em;

	/**
	 * Save the entity to the database.
	 */
	@Override
	public void save(E entity) {
		em.persist(entity);		
	}	
		
	/**
	 * Remove the entity from the database.
	 * @return true if successful and false if not
	 */
	@Override
	public boolean remove(Class<T> clazz, Long id) {		
		Object e = em.find(clazz, id);
		if (e != null) {
			em.remove(e);
			return true;
		}
		return false;
	}

	/**
	 * Find the entity in the database
	 * @return the entity
	 */
	@Override
	public T findById(Class<T> clazz, Long id) {
		return em.find(clazz, id);
	}
	
	/**
	 * Updates the entity in the database
	 * @return the entity
	 */
	@Override
	public <E> E update(E entity) {
		return em.merge(entity);
	}
	
	/**
	 * Get all entities from database
	 */
	@Override
	public abstract List<E> getAll();	
	
	/**
	 * Get max aggreagate of passed in parameter.
	 */
	public abstract String getMaxFromColumn();
	
}
