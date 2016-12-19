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
public class Profile {
    private String first_name;
    private String last_name;
    private String email;
    private int point;

    public Profile(String first_name, String last_name, String email, int point) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.point = point;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public int getPoint() {
        return point;
    }
}
