package db;

import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import model.Category;
import model.Post;

/**
 * Created on 10/28/2016.
 */
public class DataAccess {

    private String dbURL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private String dbUsername = "tr";
    private String dbPassword = "tr";
    private Connection conn;

    public DataAccess()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            if(conn != null) System.out.println("Connection successfully established.");
            else System.out.println("Could not establish connection");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static DataAccess getDataAccess(HttpSession session)
    {
        DataAccess dataAccess = (DataAccess) session.getAttribute("db");
        if(dataAccess == null) 
        {
            dataAccess = new DataAccess();
            session.setAttribute("db", dataAccess);
        }
        return dataAccess;
    }

    public boolean existUser(String username, String password)
    {
        try
        {
            String query = "select * from users where username = ? and password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public int creatUser(String username, String password, String firstName, String lastName, String email)
    {
        try
        {
            String insertCommand = "insert into users values(user_id_seq.nextval, ?, ?, ?, ?, ?, 'user', 'off', 0)";
            PreparedStatement stmt = conn.prepareStatement(insertCommand);
            stmt.setString(1, username);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, password);
            stmt.setString(5, email);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int getUserID(String username)
    {
        try
        {
            String query = "select user_id from users where username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }
            return 0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public String getUsername(int user_id)
    {
        try
        {
            String query = "select username from users where user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return rs.getString(1);
            }
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public int addPost(String username, String title, String content)
    {
        try
        {
            int user_id = getUserID(username);
            String insertCommand = "insert into posts values(post_id_seq.nextval, ?, ?, SYSDATE, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertCommand);
            stmt.setInt(1, user_id);
            stmt.setString(2, content);
            stmt.setString(3, title);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<Post> recentPosts()
    {
        ArrayList<Post> posts = new ArrayList();
        try
        {
            String query = "select * from posts order by time desc";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                posts.add(new Post(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            return posts;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public Post getPost(int post_id)
    {
        try
        {
            String query = "select * from posts where post_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                return new Post(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    
   public ArrayList<Category> getAllCategories()
   {
       ArrayList<Category> categories = new ArrayList();
        try
        {
            String query = "select * from category order by category_id asc";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                categories.add(new Category(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            return categories;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
   }

    public void printAll()
    {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            System.out.println("Successfully executed");
            while(rs.next())
            {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
