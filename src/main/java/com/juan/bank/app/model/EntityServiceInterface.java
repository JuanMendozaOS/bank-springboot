package com.juan.bank.app.model;

import java.util.List;

/**
 *
 * @author Juan Mendoza
 */

public interface EntityServiceInterface<T> {
    
    T create(T entity);
    
    T update(T entity);
    
    T findById(Long id);
    
    List<T> findAll();
    
//    void delete(Long id);
    
}
