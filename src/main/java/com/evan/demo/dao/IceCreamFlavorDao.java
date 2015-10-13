package com.evan.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evan.demo.model.IceCreamFlavor;


/**
* DAO implementation for ice cream flavor entity
*  
* @author Evan Scharfer
*/
@Transactional
@Repository
@Qualifier("iceCreamFlavorDao")
public class IceCreamFlavorDao extends BasicDAO {	
	
     
    public IceCreamFlavorDao() {}
    


	public List<IceCreamFlavor>getAll() {
        return em.createQuery("SELECT p FROM IceCreamFlavor p", IceCreamFlavor.class).getResultList();
    }

	@Override
	public String getMaxFromColumn() {		
		return null;
	}

}
