/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xp;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {
    static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    static String username = "tr";
    static String password = "tr";

    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(url, username, password);

        String sql = "INSERT INTO pictures VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "pascal.jpg");
        File image = new File("D:\\Academic\\Database Lab\\Online Forum\\trash\\Exp\\pascal.jpg");
        FileInputStream   fis = new FileInputStream(image);
        stmt.setBinaryStream(2, fis, (int) image.length());
        stmt.execute();
        fis.close();
        conn.close();
    }
} 
