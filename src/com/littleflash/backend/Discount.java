package com.littleflash.backend;


import java.util.Date;

import com.googlecode.objectify.annotation.*;

@Entity
@Cache
public class Discount {
    @Id Long dataStoreId;
    @Index Date date;
    String itemId;
    String message;
	double rate;
    
    // Empty constructor IS needed when using
    // Objectify (unless you like error 500)
    // Ignore the warning
    @SuppressWarnings("unused")
	private Discount() {}
    
    public Discount(String itemId, String message, double rate) {
        this.itemId = itemId;
    	this.message = message;
    	this.rate = rate;
    	this.date = new Date();
    }
    
    public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
    
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}