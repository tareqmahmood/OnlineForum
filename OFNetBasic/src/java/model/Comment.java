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
public class Comment {
    private String username;
    private String content;
    private String time;
    private int comment_id;

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public Comment(String username, String content, String time, int comment_id) {
        this.username = username;
        this.content = content;
        this.time = time;
        this.comment_id = comment_id;
    }

    public int getComment_id() {
        return comment_id;
    }
    
    
}
