package db;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import model.Category;
import model.Comment;
import model.DFile;
import model.File;
import model.Message;
import model.Post;
import model.User;
import model.Vote;

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
    
    public ArrayList<User> getUsers()
    {
        ArrayList<User> userList = new ArrayList();
        try
        {
            String query =  "select username, user_id from users";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                userList.add(new User(rs.getString(1), rs.getInt(2)));
            }
            return userList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
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
    
    public int addPost(int user_id, String title, String content)
    {
        try
        {
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
   
   
    public ArrayList<Comment> getComments(int post_id)
    {
        ArrayList<Comment> commentList = new ArrayList();
        try
        {
            String query =  "select u.username, c.content, to_char(c.time, 'dd-mm-yyyy') || ' at ' || to_char(c.time, 'hh:mi am') time\n" +
                            "from users u join comments c on u.user_id = c.user_id\n" +
                            "where c.post_id = ? order by c.time asc";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                commentList.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            return commentList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
   
    public int addComment(int post_id, int user_id, String content)
    {
        try
        {
            String insertCommand = "insert into comments values(comment_id_seq.nextval, ?, ?, ?, SYSDATE)";
            PreparedStatement stmt = conn.prepareStatement(insertCommand);
            stmt.setInt(1, post_id);
            stmt.setInt(2, user_id);
            stmt.setString(3, content);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public void addVote(int post_id, int user_id, int vote)
    {
        try
        {
            String exeCommand = "begin add_post_vote(?, ?, ?); end;";
            CallableStatement stmt = conn.prepareCall(exeCommand);
            stmt.setInt(1, post_id);
            stmt.setInt(2, user_id);
            stmt.setInt(3, vote);
            stmt.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Vote getVote(int post_id)
    {
        try
        {
            int upvote = 0, downvote = 0;
            String query = "select count(vote) from post_votes where vote > 0 and post_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) upvote = rs.getInt(1);
            
            query = "select count(vote) from post_votes where vote < 0 and post_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            rs = stmt.executeQuery();
            if(rs.next()) downvote = rs.getInt(1);
            
            return new Vote(upvote, downvote);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public int addMessage(int sender_id, int receiver_id, String content)
    {
        try
        {
            String insertCommand = "insert into messages values(message_id_seq.nextval, ?, ?, ?, SYSDATE)";
            PreparedStatement stmt = conn.prepareStatement(insertCommand);
            stmt.setInt(1, sender_id);
            stmt.setInt(2, receiver_id);
            stmt.setString(3, content);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<Message> getMessages(int user_id, int other_id)
    {
        ArrayList<Message> messageList = new ArrayList();
        try
        {
            String query =  "select u.user_id, u.username, to_char(m.time, 'dd-mm-yyyy') || ' at ' || to_char(m.time, 'hh:mi am') time, m.content\n" +
                            "from users u join messages m on u.user_id = m.sender_id\n" +
                            "where (m.sender_id = ? and m.receiver_id = ?) or (m.sender_id = ? and m.receiver_id = ?)\n" +
                            "order by m.time";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.setInt(2, other_id);
            stmt.setInt(3, other_id);
            stmt.setInt(4, user_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                messageList.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            return messageList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public int addCategory(int user_id , int category_id)
    {
        try
        {
            System.out.println(user_id + " " + category_id);
            String insertCommand = "insert into favourites values(? , ?)";
            PreparedStatement stmt = conn.prepareStatement(insertCommand);
            stmt.setInt(1, user_id);
            stmt.setInt(2, category_id);
            
            //stmt.setString(3, title);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<String> getFavouriteCategories(int user_id)
    {
        ArrayList<String> favouriteCategories = new ArrayList();
        try
        {
            String query =  "select c.category_name " + 
                            "from favourites f, category c " +
                            "where f.user_id = ? and f.category_id = c.category_id";
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println("OFDebug : " + user_id);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                favouriteCategories.add(rs.getString(1));
            }
            return favouriteCategories;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return favouriteCategories;
        }
    }
    
    
    
    public int addFile(int user_id, String filename, int filesize, InputStream inputStream)
    {
        if(filesize > 1000000000) return 0;
        if(inputStream == null) return 0;
        try
        {
            String insertCommand = "insert into files values(file_id_seq.nextval, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertCommand);
            stmt.setInt(1, user_id);
            stmt.setString(2, filename);
            stmt.setInt(3, filesize);
            stmt.setBlob(4, inputStream);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    
    public ArrayList<File> getFiles(int user_id)
    {
        ArrayList<File> fileList = new ArrayList();
        try
        {
            String query =  "select file_id, filename, filesize from files where user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                fileList.add(new File(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
            return fileList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return fileList;
        }
    }
    
    public DFile downloadFile(int file_id)
    {
        try
        {
            String query =  "select filename, file_data from files where file_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, file_id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return new DFile(rs.getString(1), rs.getBlob(2));
            }
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public int deleteFile(int file_id)
    {
        try
        {
            String deleteCommand = "delete files where file_id = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteCommand);
            stmt.setInt(1, file_id);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
