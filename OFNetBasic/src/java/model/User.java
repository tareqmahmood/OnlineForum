/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author HP
 */
public class User {
    private String username;
    private int user_id;

    public User(String username, int user_id) {
        this.username = username;
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public int getUser_id() {
        return user_id;
    }
}
