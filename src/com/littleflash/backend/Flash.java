package com.littleflash.backend;

import com.google.appengine.api.datastore.Key;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Flash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String itemId;
    private String userEmail;
    

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
    
    public Key getKey() {
        return key;
    }

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
