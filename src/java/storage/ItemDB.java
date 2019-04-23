package storage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author annachristofaris
 */
import database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class ItemDB {

    public void addItem(String itemCode, String itemName, String category, String title, String rating, String descrip, String userID) {
        Item newItem = new Item();
        newItem.setItemCode(itemCode);
        newItem.setItemName(itemName);
        newItem.setCategory(category);
        newItem.setTitle(title);
        newItem.setDescrip(descrip);
        newItem.setRating(rating);
        newItem.setUserID(userID);
        this.addItem(newItem);
    }

    public void addItem(Item item) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO user (itemCode, itemName, category, title, descrip, rating, userID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, item.getItemCode());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getCategory());
            ps.setString(4, item.getTitle());
            ps.setString(5, item.getDescrip());
            ps.setString(6, item.getRating());
            ps.setString(7, item.getUserID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public void addUserItem(UserProfile up, String itemCode) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String userID = up.getUser().getUserID();

        String query
                = "INSERT INTO userItems (itemCode, userID) "
                + "VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            ps.setString(2, userID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        up.addUserItem(itemCode);
    }

    public void removeUserItem(UserProfile up, String itemCode) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM userItems "
                + "WHERE itemCode = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        up.removeItem(itemCode);

    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM item ";
        try {

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("itemCode"));
                item.setItemName(rs.getString("itemName"));
                item.setCategory(rs.getString("category"));
                item.setTitle(rs.getString("title"));
                item.setDescrip(rs.getString("descrip"));
                item.setUserID(rs.getString("userID"));
                items.add(item);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: " + "ItemDB.getAllItems()");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return items;
    }


    
     public  ArrayList<UserItem> getAllUserItems(String userID) {
        //ArrayList<UserItem> uItems = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserItem> uItems = new ArrayList<>();
        
               String query = "SELECT * FROM userItems "
                + "WHERE UserID = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            
            ItemDB db = new ItemDB();
            
            while (rs.next()) {              
                UserItem itemRating = new UserItem();
                String itemCode = rs.getString("itemCode");
                Item i = db.getItem(itemCode);
                itemRating.setItem(i);
                itemRating.setRating(rs.getString("rating"));
                itemRating.setItemCode(itemCode);
                itemRating.setUserID(userID);
                itemRating.setMadeIt(rs.getBoolean("madeIt"));
                uItems.add(itemRating);
            }
         
        } catch (SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: " + "ItemDB.getAllUserItems()");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return uItems;

    }

    public Item getItem(String itemCode) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        Item item = new Item();
        String query = "SELECT * FROM item " + "WHERE itemCode = ?";
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                item.setItemCode(rs.getString("itemCode"));
                item.setItemName(rs.getString("itemName"));
                item.setCategory(rs.getString("category"));
                item.setTitle(rs.getString("title"));
                item.setDescrip(rs.getString("descrip"));
                item.setUserID(rs.getString("userID"));
            }

        } catch (SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: " + "ItemDB.getAllItems()");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return item;
    }

    public static ArrayList<Item> getItemsByCat(String category) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM item WHERE category = ?";
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, category);
            rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("itemCode"));
                item.setItemName(rs.getString("itemName"));
                item.setCategory(rs.getString("category"));
                item.setTitle(rs.getString("title"));
                item.setDescrip(rs.getString("descrip"));
                item.setUserID(rs.getString("userID"));
                items.add(item);
            }

        } catch (SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: " + "items by category");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return items;
    }

    //update rating in userItem table
    public void updateFeedback(UserProfile up, String itemCode, String rating) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE userItems "
                + "SET rating = ? "
                + "WHERE itemCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, rating);
            ps.setString(2, itemCode);
            ps.executeUpdate();
        } catch (SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: " + "Item.changeRating()");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        up.updateRating(itemCode, rating);

    }
    
    //update boolean in userItem table
    public void updateFavorited(UserProfile up, String itemCode, Boolean madeIt) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE userItems "
                + "SET madeIt = ? "
                + "WHERE itemCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, madeIt);
            ps.setString(2, itemCode);

            ps.executeUpdate();
        } catch (SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: " + "Item.changeRating()");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        up.updateFav(itemCode, madeIt);
    }
}
