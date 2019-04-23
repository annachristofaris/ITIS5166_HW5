package storage;


import java.io.Serializable;

/**
 *
 * @author annachristofaris
 */
public class Item implements Serializable {
    private String itemCode, itemName, category, title, descrip, rating, userID, imageURL;

    public Item() {
        itemCode ="";
        itemName="";
        category="";
        title="";   
        descrip="";
        rating = "";
        userID = "";
        imageURL = "";
    }
    
      public Item(String itemCode, String itemName, String category, String title, String descrip, String userID) {
        
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.title = title;
        this.descrip = descrip;
        this.userID = userID;

        
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
        this.imageURL = "./images/" + itemCode + ".png";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageURL() {
      String newUrl = "./images/" + itemCode + ".png";
        return newUrl;
    }
}

