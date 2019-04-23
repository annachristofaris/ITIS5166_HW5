package storage;

import database.ConnectionPool;
import database.DBUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author annachristofaris
 */
public class UserProfile implements Serializable {

    private User user;
    private ArrayList<UserItem> itemList;
    private String userID;

    public UserProfile() {
       itemList = new ArrayList<>();

    }

    public UserProfile(User user) {
        this.user = user;
        this.itemList = new ArrayList<>();
        this.userID = this.user.getUserID();
    }
//    getters and setters

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<UserItem> getItemList() {
//      
        return itemList;
    }
    
    public void getSavedItems() {
         ItemDB database = new ItemDB();
         ArrayList temp = database.getAllUserItems(this.userID);
           Iterator<UserItem> itr = temp.iterator();
           while (itr.hasNext()) {
               UserItem currentItem = itr.next();
               itemList.add(currentItem);
           }
         
    }


    public void addItem(Item item, String rating, Boolean madeIt) {
        boolean exists = false;
        for (int i = 0; i < itemList.size(); i++) {
            //check if profile already contains this item
            if (itemList.get(i).getItemCode().equals(item.getItemCode())) {
                exists = true;
                break;
            }
        }
        // add new userItem if it wasn't in the list
        if (!exists) {
            UserItem userItem = new UserItem();
            userItem.setItem(item);
            userItem.setItemCode(item.getItemCode());
            userItem.setRating(rating);
            userItem.setMadeIt(madeIt);
            itemList.add(userItem);
        }
    }

    //method to add new User Items
    public void addUserItem(String itemCode) {
        Item item = findItemInDB(itemCode);
        //default values for new items
        //addItem method will check for duplicates
        addItem(item, "0", false);
    }

    public void removeItem(String itemCode) {
        //using an iterator to go through UserItem list and remove associated item(s) 
        Iterator<UserItem> itr = itemList.iterator();
        while (itr.hasNext()) {
            UserItem currentItem = itr.next();
            if (currentItem.getItemCode().equals(itemCode)) {
                itr.remove();
            }
        }
    }

    public void updateRating(String itemCode, String rating) {
        Iterator<UserItem> itr = itemList.iterator();
        while (itr.hasNext()) {
            UserItem currentItem = itr.next();
            if (currentItem.getItemCode().equals(itemCode)) {
                currentItem.setRating(rating);
            }
        }
    }

    public void updateFav(String itemCode, Boolean madeIt) {
        Iterator<UserItem> itr = itemList.iterator();
        while (itr.hasNext()) {
            UserItem currentItem = itr.next();
            if (currentItem.getItemCode().equals(itemCode)) {
                currentItem.setMadeIt(madeIt);
            }
        }
    }

    public List<UserItem> getItems() {
        return itemList;
    }

    public void emptyProfile() {
        itemList.clear();
    }

    public Item findItemInDB(String itemCode) {
        ItemDB database = new ItemDB();
        Item newItem = database.getItem(itemCode);
        return newItem;
    }

    public boolean inList(String itemCode) {
        boolean exists = false;
        Iterator<UserItem> itr = itemList.iterator();
        while (itr.hasNext()) {
            UserItem currentItem = itr.next();
            if ((currentItem.getItemCode().equals(itemCode)) || (currentItem.getItem().getItemCode().equals(itemCode))) {
                exists = true;
            }
        }
        return exists;
    }

}
