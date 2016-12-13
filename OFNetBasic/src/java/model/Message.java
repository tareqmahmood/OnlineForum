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

    public Message(String username, String datetime, String content) {
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
    
    
}
