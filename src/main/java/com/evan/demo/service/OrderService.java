
package com.evan.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.evan.demo.dao.DAO;
import com.evan.demo.model.ICOrder;
import com.evan.demo.model.IceCreamFlavor;
import com.evan.demo.model.OrderItem;

@Service 
public class OrderService {
	
private static final Logger logger = LoggerFactory.getLogger(IceCreamService.class);
	
	@Autowired
	@Qualifier("ICOrderDao")
    private DAO<ICOrder, ICOrder> iCOrderDao;
	
	@Autowired
	@Qualifier("OrderItemDao")
    private DAO<OrderItem, OrderItem> orderItemDao;
	
	@Autowired
	IceCreamService ics;
	
	/**
	 * Get the completed orders of all
	 * @return
	 */
	public List<ICOrder> getCompleteOrders() {
		return iCOrderDao.getAll();
	}
	
	/**
	 * Get the total sales of all order
	 * @return all sales
	 */
	public String getTotalSales() {
		return iCOrderDao.getMaxFromColumn();
	}
	
	/**
	 * Creates a new quote orde of 0 value
	 * @return ICCOrder
	 */
	public ICOrder createQuote() {
		ICOrder newOrder = new ICOrder(new BigDecimal("0.00"));
		// save new order to database
		iCOrderDao.save(newOrder);		
		return newOrder;
	}
	
	/**
	 * Addes a flavor to the qutoe
	 * @param quoteId
	 * @param flavorId
	 * @param scoops
	 * @return The updated quote
	 */
	public ICOrder addItemToQuote(long quoteId, long flavorId, int scoops) {		
		ICOrder order = iCOrderDao.findById(ICOrder.class, quoteId);
		IceCreamFlavor flavor = ics.getFlavor(flavorId);
		if (order != null && flavor != null && flavor.getInventory() >= scoops) {
			//create the new item
			OrderItem item = new OrderItem(order,flavor,scoops);
			// add to db
			orderItemDao.save(item);
			// get the new order with added item
			order = iCOrderDao.findById(ICOrder.class, quoteId);
		} else if (flavor.getInventory() < scoops) {
			logger.debug("No inventory, " + flavor.getInventory() + " for scoops:" + scoops );
		}
		
		return order;
	}
	
	/**
	 * Convert the quote to an order
	 * @param quoteId
	 * @return Updated order
	 */
	public ICOrder convertQuoteToOrder(long quoteId) {
		ICOrder order = iCOrderDao.findById(ICOrder.class, quoteId);
		BigDecimal total = new BigDecimal("0.00");
		order.setOrderType(1);
		// calculate order total
		for (OrderItem item: order.getItems()) {
			total = total.add(item.getTotalItemPrice());
			//decremit inventory
			int newInventory = item.getFlavor().getInventory() - 1;
			ics.updateInventory(item.getFlavor().getId(), newInventory);
		}
		order.setTotal(total);
		order = iCOrderDao.update(order);
		
		return order;
	}

}
