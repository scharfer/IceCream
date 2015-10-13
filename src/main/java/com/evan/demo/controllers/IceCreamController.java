package com.evan.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evan.demo.model.AdminUser;
import com.evan.demo.model.IceCreamFlavor;
import com.evan.demo.service.AdminService;
import com.evan.demo.service.IceCreamService;
import com.evan.demo.validators.PriceValidator;

@Controller
public class IceCreamController {
	
	private static final Logger logger = LoggerFactory.getLogger(IceCreamController.class);
	
	@Autowired
	IceCreamService ics;
	
	@Autowired
	AdminService as;
		
	/**
	 * This will return the complete list of flavors in a JSON object.
	 * @return JSON of List IceCreamFlavor
	 */
	@RequestMapping(value="/icecream/flavors", method = RequestMethod.GET)
	public @ResponseBody List<IceCreamFlavor> getFlavors() {		
		// get the full list of flavors
		return ics.getAllFlavors();
	}
	
	/**
	 * This will add the passed in flavor to the database and return the complete list of flavors via JSON.
	 * TODO update to use model validator
	 * @param name
	 * @param price
	 * @return JSON of List IceCreamFlavor
	 */
	@RequestMapping(value="/admin/icecream/add", method = RequestMethod.GET)
	public @ResponseBody List<IceCreamFlavor> addFlavor(@RequestParam(value="name") String name,
			@RequestParam(value="price") @Validated(value=PriceValidator.class) String price, 
			@RequestParam(value="inventory", required=false, defaultValue="0") int inventory,
			@RequestParam(value="userName") String userName, 
			@RequestParam(value="AuthToken", defaultValue="") String authToken) {
		
		AdminUser user = as.validateUser(userName,authToken);
		if (user != null) {
			if (inventory < 0) {
				inventory = 0;
				logger.debug("Invalid inventory value. " + inventory);
			}
			
			// make sure ice cream is valid
			if (!name.equals("")) {
				// add the ice cream flavor
				ics.addFlavor(name, price,inventory);
			} else {
				logger.debug("Empty name value.");
			}
			// get the full list of flavors
			return ics.getAllFlavors();
		} else {
			return null;
		}
	}
	
	/**
	 * This will remove the passed in flavor from the database and return the complete list of flavors via JSON.
	 * 
	 * @param flavor id	
	 * @return JSON of List IceCreamFlavor
	 */
	@RequestMapping(value="/admin/icecream/remove/{icid}", method = RequestMethod.GET)
	public @ResponseBody List<IceCreamFlavor> removeFlavor(@PathVariable("icid") long icId,
			@RequestParam(value="userName") String userName, 
			@RequestParam(value="AuthToken", defaultValue="") String authToken) {
		AdminUser user = as.validateUser(userName,authToken);
		if (user != null) {			
			
			if (icId < 0) {
				return null;
			}			
			// add the ice cream flavor
			ics.removeFlavor(icId);
			// get the full list of flavors
			return ics.getAllFlavors();
		} else {
			return null;
		}
	}
		
	
	/**
	 * This will update the inventory of the passed in flavor and then return that flavor.
	 * 
	 * @param flavor id
	 * @param inventory
	 * @return JSON of IceCreamFlavor
	 */
	@RequestMapping(value="/admin/icecream/inventory/{icid}/{inventory}", method = RequestMethod.GET)
	public @ResponseBody IceCreamFlavor updateInventory(@PathVariable("icid") long icId,
			@PathVariable("inventory") int inventory,
			@RequestParam(value="userName") String userName, 
			@RequestParam(value="AuthToken", defaultValue="") String authToken) {
		
		AdminUser user = as.validateUser(userName,authToken);
		if (user != null) {			
			if (icId <= 0 || inventory < 0) {
				logger.debug("Invalid id value. " + icId);
				return null;
			}
			// add the ice cream flavor
			return ics.updateInventory(icId, inventory);
		} else {
			return null;
		}
				
	}
	
	/**
	 * This will update the price of the passed in flavor and then return that flavor.
	 * 
	 * @param flavor id
	 * @param inventory
	 * @return JSON of IceCreamFlavor
	 */
	@RequestMapping(value="/admin/icecream/price/{icid}", method = RequestMethod.GET)
	public @ResponseBody IceCreamFlavor updatePrice(@PathVariable("icid") long icId,
			@RequestParam("price") String price,
			@RequestParam(value="userName") String userName, 
			@RequestParam(value="AuthToken", defaultValue="") String authToken) {
		
		AdminUser user = as.validateUser(userName,authToken);
				
		if (user != null) {	
			// add the ice cream flavor
			if (icId <= 0) {				
				logger.debug("Invalid id value. " + icId);
				return null;
			}
			return ics.updatePrice(icId, price);		
		} else {
			return null;
		}
		
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new PriceValidator());
    }

}
