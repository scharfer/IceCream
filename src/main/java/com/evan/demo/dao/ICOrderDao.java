package com.evan.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evan.demo.model.ICOrder;


/**
* DAO implementation for ice cream flavor entity
*  
* @author Evan Scharfer
*/
@Transactional
@Repository
@Qualifier("ICOrderDao")
public class ICOrderDao extends BasicDAO {
    
	/**
	 * Gets all the orders
	 * @return A List of orders
	 */
    public List<ICOrder>getAll() {
        return em.createQuery("SELECT p FROM ICOrder p where orderType = :orderType", ICOrder.class).setParameter("orderType", 1).getResultList();
    }    
    
    /**
     * Get the total sales of orders.
     */
    @Override
    public String getMaxFromColumn() {
    	String qlString = "SELECT SUM(total) FROM ICOrder where orderType =:orderType";
    	BigDecimal result = em.createQuery(qlString, BigDecimal.class).setParameter("orderType", 1).getSingleResult();
    	return result.toPlainString();    	
    }

}
