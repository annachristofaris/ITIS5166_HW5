package storage;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author annachristofaris
 */
public class UserItem implements Serializable {
    private Item item; 
    private String itemCode;
    private String userID;
    private Boolean madeIt;
    private String rating;
    
    public UserItem() {
//       rating = "";  
//       itemCode ="";
//       userID ="";
      
    }

    public Boolean getMadeIt() {
        return madeIt;
    }

    public void setMadeIt(Boolean madeIt) {
        this.madeIt = madeIt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
//    String rating; 
//    boolean madeIt;
    
 
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


 
    
}
