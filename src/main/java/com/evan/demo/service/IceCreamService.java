package com.evan.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evan.demo.dao.DAO;
import com.evan.demo.model.IceCreamFlavor;

@Service 
public class IceCreamService {
	
	private static final Logger logger = LoggerFactory.getLogger(IceCreamService.class);
	
	@Autowired	
    private DAO iceCreamFlavorDao;
	
	/**
	 * Calls the DAO to get all the flavors
	 * @return A list of flavors
	 */
	public List<IceCreamFlavor> getAllFlavors() {	
		return iceCreamFlavorDao.getAll();
	}
	
	/**
	 * Add a flavor to the database
	 * @param name
	 * @param price
	 * @return if successful or not
	 */
	public boolean addFlavor(String name, String price, int inventory) {
		// build the big decimal
		BigDecimal bdPrice = null;
		try {
			bdPrice = new BigDecimal(price);
		} catch (NumberFormatException nfe) {
			logger.error("Price not in valid format: " + price);
			// bad format
			return false;
		}
		// build new flavor object
		IceCreamFlavor newFlavor = new IceCreamFlavor(name,bdPrice, inventory);
		// save to the database
		iceCreamFlavorDao.save(newFlavor);
		return true;		
	}
	
	/**
	 * Removes the flavor from the database
	 * @param id
	 * @return
	 */
	public boolean removeFlavor(Long id) {		
		return iceCreamFlavorDao.remove(IceCreamFlavor.class, id);		
	}
	
	/**
	 * Update the inventory of a flavor at the db level.
	 * @param id
	 * @param newInventoryValue
	 * @return the updated IceCreamFlavor
	 */
	public IceCreamFlavor updateInventory(Long id, int newInventoryValue) {
		// get the reference of the flavor
		IceCreamFlavor oldFlavor = (IceCreamFlavor)iceCreamFlavorDao.findById(IceCreamFlavor.class, id);
		if (oldFlavor != null) {
			// if exists
			oldFlavor.setInventory(newInventoryValue);
			return (IceCreamFlavor)iceCreamFlavorDao.update(oldFlavor);			
		}
		// flavor updating not found
		return null;		
		
	}
	
	/**
	 * Update the price of a flavor at the db level.
	 * @param id
	 * @param price
	 * @return the updated IceCreamFlavor
	 */
	public IceCreamFlavor updatePrice(Long id, String price) {
		// get the reference of the flavor
		IceCreamFlavor oldFlavor = (IceCreamFlavor)iceCreamFlavorDao.findById(IceCreamFlavor.class, id);
		if (oldFlavor != null) {
			// build the big decimal
			BigDecimal bdPrice = null;
			try {
				bdPrice = new BigDecimal(price);
			} catch (NumberFormatException nfe) {
				logger.error("Price not in valid format: " + price);
				// bad format
				return oldFlavor;
			}
			// if exists
			oldFlavor.setPrice(bdPrice);
			return (IceCreamFlavor)iceCreamFlavorDao.update(oldFlavor);			
		}
		// flavor updating not found
		return null;		
		
	}
	
	/**
	 * Gets a flavor at the db level.
	 * @param id
	 * @return the IceCreamFlavor
	 */
	public IceCreamFlavor getFlavor(Long id) {
		return (IceCreamFlavor)iceCreamFlavorDao.findById(IceCreamFlavor.class, id);
	}

}
