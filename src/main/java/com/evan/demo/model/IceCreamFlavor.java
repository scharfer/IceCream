package com.evan.demo.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity object for the ice cream flavor table.
 * @author escharfer
 *
 */
@Entity
public class IceCreamFlavor {
	
	@Id
    @GeneratedValue
    private Long id;
    private String flavorName;
    private BigDecimal price;
    private Integer inventory;
    
    public IceCreamFlavor() {}
    
	public IceCreamFlavor(String flavorName, BigDecimal price) {		
		this.flavorName = flavorName;
		this.price = price;
	}
	
	public IceCreamFlavor(String flavorName, BigDecimal price, int inventory) {		
		this.flavorName = flavorName;
		this.price = price;
		this.inventory = inventory;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlavorName() {
		return flavorName;
	}
	public void setFlavorName(String flavorName) {
		this.flavorName = flavorName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}    

}
