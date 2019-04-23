package storage;

import database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class UserDB {

    private static UserDB userDatabase = new UserDB();
    List<User> users;
    List<UserProfile> userProfiles;

    /**
     * Constructor to add a user
     *
     * @param firstName
     * @param lastName
     * @param email
     *
     * @return
     */
    public UserDB() {

    }

    public void addUser(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO user (userID, firstName, lastName, email, password)"
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList();

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user ";

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString("userID"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement in: " + "UserDB.getAllUsers()");
            System.out.println("ERROR: Could not exicute SQL statement: " + se);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return users;

    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public static UserDB getUserDatabase() {
        return userDatabase;
    }

    public Item getItem(UserProfile userprofile, String itemCode) {
        Item temp = new Item();
        for (int i = 0; i < userProfiles.size(); i++) {
            if (userProfiles.get(i).equals(userprofile)) {
                for (int x = 0; x < userProfiles.get(i).getItems().size(); x++) {
                    if (userProfiles.get(i).getItems().get(x).getItem().getItemCode().equals(itemCode)) {
                        temp = userProfiles.get(i).getItems().get(x).getItem();
                    }
                }
            }
        }
        return temp;
    }

}
