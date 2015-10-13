package com.evan.demo.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Item added to order or basket
 * @author escharfer
 *
 */
@Entity
public class OrderItem {
	
	@Id
    @GeneratedValue
	private Long orderItemId;
	
	@ManyToOne
	@JsonIgnore
	private ICOrder order;
	
	@OneToOne
	private IceCreamFlavor flavor;
	
	private int scoops;
	private BigDecimal totalItemPrice;
	
	
	public OrderItem() {}	
	
	public OrderItem(ICOrder order, IceCreamFlavor flavor, int scoops) {		
		this.order = order;
		this.flavor = flavor;
		this.scoops = scoops;
		this.totalItemPrice = flavor.getPrice().multiply(new BigDecimal(String.valueOf(scoops)));
	}	
	
	public Long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public ICOrder getOrder() {
		return order;
	}
	public void setOrder(ICOrder order) {
		this.order = order;
	}
	public IceCreamFlavor getFlavor() {
		return flavor;
	}
	public void setFlavor(IceCreamFlavor flavor) {
		this.flavor = flavor;
	}
	public int getScoops() {
		return scoops;
	}
	public void setScoops(int scoops) {
		this.scoops = scoops;
	}
	public BigDecimal getTotalItemPrice() {
		return totalItemPrice;
	}
	public void setTotalItemPrice(BigDecimal totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}
	
	

}
