package com.evan.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evan.demo.service.AdminService;

/**
 * Controller to made the Admin RESTFUL requests.
 * @author escharfer
 *
 */
@Controller
public class AdminController {
	
	@Autowired
	AdminService as;
	
	/**
	 * Logs in the user and returns a new token
	 * @return JSON of List IceCreamFlavor
	 */
	@RequestMapping(value="/admin/login", method = RequestMethod.GET)
	public @ResponseBody String login(@RequestParam(value="userName") String userName,
			@RequestParam(value="password") String password) {		
		// get the full list of flavors
		return as.getAuthToken(userName, password);
	}

}
