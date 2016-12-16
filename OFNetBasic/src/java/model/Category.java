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
public class Category {
    private int category_id;
    private int parent_categoty;
    private String category_name;
    
    public static final Category motherCategory = new Category(1, 0, "All Categories");

    public Category(int category_id, int parent_categoty, String category_name) {
        this.category_id = category_id;
        this.parent_categoty = parent_categoty;
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getParent_categoty() {
        return parent_categoty;
    }

    public String getCategory_name() {
        return category_name;
    }
    
    
}
