package com.evan.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evan.demo.model.AdminUser;
import com.evan.demo.model.ICOrder;
import com.evan.demo.service.AdminService;
import com.evan.demo.service.OrderService;

@Controller
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService os;
	
	@Autowired
	AdminService as;
	
	/**
	 * Creates a new quote order
	 * @return ICOrder of new quote
	 */
	@RequestMapping(value="/order/create", method = RequestMethod.GET)
	public @ResponseBody ICOrder createQuote() {
		return os.createQuote();
	}
	
	/**
	 * This is used to add a scoop to the basket. The order is a quote at this time.
	 * @param quoteId
	 * @param flavorId
	 * @param scoops
	 * @return The updated quote
	 */
	@RequestMapping(value="/order/add/{quoteId}/{flavorId}/{scoops}", method = RequestMethod.GET)
	public @ResponseBody ICOrder addItemToQuote(@PathVariable("quoteId") long quoteId,
			@PathVariable("flavorId") long flavorId,
			@PathVariable("scoops") int scoops) {
		
		if (flavorId <= 0 || quoteId <= 0 || scoops <= 0 ) {
			logger.error("Invalid data: flavorID:" + flavorId + " quoteId: " + quoteId + " scoops: " + scoops);
			return null;
		}		
		
		ICOrder updatedOrder = os.addItemToQuote(quoteId, flavorId, scoops);
		
		return updatedOrder;
	}
	
	/**
	 * This is used when the user is ready to complete their order. It will convert the qoute to an order.
	 * @param quoteId
	 * @return The updated order
	 */
	@RequestMapping(value="/order/{quoteId}", method = RequestMethod.GET)
	public @ResponseBody ICOrder convertQuoteToOrder(@PathVariable("quoteId") long quoteId) {
		if (quoteId <= 0) {
			logger.error("Invalid data: quoteId: " + quoteId);
			return null;
		}
		return os.convertQuoteToOrder(quoteId);
	}
	
	/**
	 * This is will get the total sales to date.
	 * @return String of the total sales amount
	 */
	@RequestMapping(value="/admin/order/totalSales", method = RequestMethod.GET)
	public @ResponseBody String getTotalSales(@RequestParam(value="userName") String userName, 
			@RequestParam(value="AuthToken", defaultValue="") String authToken) {
		AdminUser user = as.validateUser(userName,authToken);
		if (user != null) {			
			return os.getTotalSales();
		} else {
			logger.error("Not Authorized");
			return "Not Authorized";
		}
	}
	
	/**
	 * This will return a list of orders
	 * @return List of completed orders
	 */
	@RequestMapping(value="/admin/order/allOrders", method = RequestMethod.GET)
	public @ResponseBody List<ICOrder> getCompletedOrders(@RequestParam(value="userName") String userName, 
			@RequestParam(value="AuthToken", defaultValue="") String authToken) {
		AdminUser user = as.validateUser(userName,authToken);
		if (user != null) {	
			return os.getCompleteOrders();
		} else {
			logger.error("Not Authorized");
			return null;
		}
	}
	
	
}
