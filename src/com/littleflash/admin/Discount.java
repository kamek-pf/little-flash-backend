package com.littleflash.admin;

import java.util.Date;
import com.googlecode.objectify.annotation.*;

@Entity
@Cache
public class Discount {
    @Id Long id;
    @Index Date date;
    
    String item_id;
    String item_name;
    String message;
	double rate;
	double old_price;
	double new_price;
    
    // Empty constructor IS needed when using
    // Objectify (unless you like error 500)
    // Ignore the warning
    @SuppressWarnings("unused")
	private Discount() {}
    
    public Discount(String item_id, String message, double rate) {
        this.item_id = item_id;
    	this.message = message;
    	this.rate = rate;
        this.date = new Date();
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public String getItemID() {
        return item_id;
    }
    
    public void setItemID(String item_id) {
        this.item_id = item_id;
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