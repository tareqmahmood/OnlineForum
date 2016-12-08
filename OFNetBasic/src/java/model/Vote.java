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
public class Vote {
    private int upvote;
    private int downvote;

    public Vote(int upvote, int downvote) {
        this.upvote = upvote;
        this.downvote = downvote;
    }

    public int getUpvote() {
        return upvote;
    }

    public int getDownvote() {
        return downvote;
    }
    
    
}
