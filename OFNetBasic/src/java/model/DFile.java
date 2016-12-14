/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Blob;

/**
 *
 * @author HP
 */
public class DFile {
    public String filename;
    public Blob blob;

    public DFile(String filename, Blob blob) {
        this.filename = filename;
        this.blob = blob;
    }
}
