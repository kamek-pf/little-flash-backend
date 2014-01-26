package com.littleflash.backend;

import com.googlecode.objectify.annotation.*;

@Entity
@Cache
public class Item {
    @Id Long dataStoreId;
    String itemId;
    String itemName;
    String info;
	double price;
    
	// Empty constructor IS needed when using
    // Objectify (unless you like error 500)
    // Ignore the warning
    @SuppressWarnings("unused")
	private Item() {}
    
    public Item(String itemId, String itemName, String info, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
    	this.info = info;
    	this.price = price;
    }

    public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}