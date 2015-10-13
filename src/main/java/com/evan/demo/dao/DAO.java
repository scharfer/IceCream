package com.evan.demo.dao;

import java.util.List;

/**
 * Basic DAO interface
 * @author escharfer
 *
 * @param <E>
 * @param <T>
 */
public interface DAO<E, T> {
    public void save(E entity);
    
    public boolean remove(Class<T> clazz, Long id);
    
    public <E> E update(E entity);
    
    public T findById(Class<T> clazz, Long id);    
    
    public List<E> getAll();
    
    public abstract String getMaxFromColumn();
}
