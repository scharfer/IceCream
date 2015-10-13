package com.evan.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evan.demo.HomeController;
import com.evan.demo.model.AdminUser;

/**
 * The USER dao to query the admin table
 * @author escharfer
 *
 */
@Transactional
@Repository
@Qualifier("adminUserDao")
public class AdminUserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@PersistenceContext
    protected EntityManager em;	
	
	/**
	 * Try to find the user with matching password in the database. Password should be encrypted in live environment.
	 * @param userName
	 * @param password
	 * @return AdminUser the found user
	 */
	public AdminUser findUser(String userName, String password) {
		String qlString = "SELECT p FROM AdminUser p where userName =:userName and password =:password";
    	AdminUser result = null;
    	try {
    		result = em.createQuery(qlString, AdminUser.class).setParameter("userName", userName)
    			.setParameter("password", password).getSingleResult();
    	} catch (javax.persistence.NoResultException jpn) {
			logger.debug("User: " + userName + " not found with password:" + password);
			result = null;
		}
    	return result;		
	}
	
	/**
	 * Try to find the user with the matching token.
	 * @param userName
	 * @param token
	 * @return AdminUser the found user
	 */
	public AdminUser findUserByToken(String userName, String token) {
		String qlString = "SELECT p FROM AdminUser p where userName =:userName and authToken =:authToken";
		AdminUser result = null;
		try {
	    	result = em.createQuery(qlString, AdminUser.class).setParameter("userName", userName)
	    			.setParameter("authToken", token).getSingleResult();
		} catch (javax.persistence.NoResultException jpn) {
			logger.debug("User: " + userName + " not found with token:" + token);
			result = null;
		}
    	return result;		
	}
	
	/**
	 * Update the admin user in the database.
	 * @param entity
	 * @return AdminUser
	 */
	public AdminUser update(AdminUser entity) {
		return em.merge(entity);
	}

}
