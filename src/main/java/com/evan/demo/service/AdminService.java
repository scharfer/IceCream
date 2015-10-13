package com.evan.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evan.demo.dao.AdminUserDAO;
import com.evan.demo.model.AdminUser;

@Service 
public class AdminService {
	
	@Autowired	
    private AdminUserDAO adminUserDAO;
	
	/**
	 * Generates a new auth token for the user
	 * @param userName
	 * @param password
	 * @return String authToken
	 */
	public String getAuthToken(String userName, String password) {
		AdminUser user = adminUserDAO.findUser(userName, password);
		String uuidStr = "Unauthorized";
		if (user != null) {
			// generate simple auth token (more secure in non demo env)
			UUID uuid = UUID.randomUUID();
			uuidStr = uuid.toString();
			user.setAuthToken(uuidStr);
			adminUserDAO.update(user);			
		}
		return uuidStr;
	}
	
	/**
	 * Validates the user with the token in the database. 
	 * @param userName
	 * @param token
	 * @return AdminUser
	 */
	public AdminUser validateUser(String userName, String token) {
		AdminUser user = adminUserDAO.findUserByToken(userName, token);
		return user;
	}

}
