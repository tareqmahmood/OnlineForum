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
public class Message {
    private String username;
    private String datetime;
    private String content;
    private int user_id;

    public Message(int user_id, String username, String datetime, String content) {
        this.user_id = user_id;
        this.username = username;
        this.datetime = datetime;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getContent() {
        return content;
    }

    public int getUser_id() {
        return user_id;
    }
}
