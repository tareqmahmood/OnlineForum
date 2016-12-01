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
public class Post {
    private int post_id;
    private int user_id;
    private String content;
    private String datetime;
    private String title;

    public Post(int post_id, int user_id, String content, String datetime, String title) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
        this.datetime = datetime;
        this.title = title;
    }

    

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getPost_id() {
        return post_id;
    }  
    
    
}
