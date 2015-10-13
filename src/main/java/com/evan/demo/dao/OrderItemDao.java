package com.evan.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evan.demo.model.OrderItem;


/**
* DAO implementation for ice cream flavor entity
*  
* @author Evan Scharfer
*/
@Transactional
@Repository
@Qualifier("OrderItemDao")
public class OrderItemDao extends BasicDAO {
     
    public List<OrderItem>getAll() {
        return em.createQuery("SELECT p FROM ORDERITEM p", OrderItem.class).getResultList();
    }
    
    @Override
	public String getMaxFromColumn() {		
		return null;
	}

}
