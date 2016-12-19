package db;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import model.Category;
import model.Comment;
import model.DFile;
import model.File;
import model.Message;
import model.Post;
import model.Profile;
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
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<User> userList = new ArrayList();
        try
        {
            String query =  "select username, user_id from users";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                userList.add(new User(rs.getString(1), rs.getInt(2)));
            }
            if(stmt != null) stmt.close();
            return userList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean existUser(String username, String password)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "select * from users where username = ? and password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int creatUser(String username, String password, String firstName, String lastName, String email)
    {
        PreparedStatement stmt = null;
        try
        {
            String insertCommand = "insert into users values(user_id_seq.nextval, ?, ?, ?, ?, ?, 'user', 'off', 0)";
            stmt = conn.prepareStatement(insertCommand);
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
        }finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getUserID(String username)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "select user_id from users where username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
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
        
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public String getUsername(int user_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "select username from users where user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int addPost(int user_id, String title, String content, String[] ctgs, String[] files)
    {
        PreparedStatement stmt = null;
        try
        {
            String sequence = "select POST_ID_SEQ.nextval from dual";
            stmt = conn.prepareStatement(sequence);
            ResultSet rs = stmt.executeQuery();
            int post_id = 0; 
                if(rs.next())
                  post_id = rs.getInt(1);
            String insertCommand = "insert into posts values(?, ?, ?, SYSDATE, ?)";
            stmt = conn.prepareStatement(insertCommand);
            stmt.setInt(1, post_id);
            stmt.setInt(2, user_id);
            stmt.setString(3, content);
            stmt.setString(4, title);
            int count = stmt.executeUpdate();
            
            if (ctgs != null) 
            {
                for (int i = 0; i < ctgs.length; i++) 
                {
                    insertCommand = "insert into post_category values(?, ?)";
                    stmt = conn.prepareStatement(insertCommand);
                    stmt.setInt(1, post_id);
                    stmt.setInt(2, Integer.parseInt(ctgs[i]));
                    count = stmt.executeUpdate();
                }
            }
            
            if (files != null) 
            {
                for (int i = 0; i < files.length; i++) 
                {
                    insertCommand = "insert into attachments values(?, ?)";
                    stmt = conn.prepareStatement(insertCommand);
                    stmt.setInt(1, post_id);
                    stmt.setInt(2, Integer.parseInt(files[i]));
                    count = stmt.executeUpdate();
                }
            }
            
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Post> recentPosts()
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList();
        try
        {
            String query =  "select post_id, user_id, content, to_char(time, 'dd-mm-yyyy') || ' at ' || to_char(time, 'hh:mi am') datetime, title "
                            + "from posts order by time desc";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Post getPost(int post_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query =  "select post_id, user_id, content, to_char(time, 'dd-mm-yyyy') || ' at ' || to_char(time, 'hh:mi am') datetime, title \n" +
                            "from posts where post_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            rs = stmt.executeQuery();
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
        }finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<File> getAttachments(int post_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<File> attaList = new ArrayList();
        try
        {
            String query =  "select file_id, filename, filesize from files where file_id in (select file_id from attachments where post_id = ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                attaList.add(new File(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
            return attaList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return attaList;
        }finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
   public ArrayList<Category> getAllCategories()
   {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Category> categories = new ArrayList();
        try
        {
            String query = "select * from category order by category_id asc";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
   
    public ArrayList<Comment> getComments(int post_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Comment> commentList = new ArrayList();
        try
        {
            String query =  "select u.username, c.content, to_char(c.time, 'dd-mm-yyyy') || ' at ' || to_char(c.time, 'hh:mi am') time, c.comment_id\n" +
                            "from users u join comments c on u.user_id = c.user_id\n" +
                            "join post_comment pc on pc.comment_id = c.comment_id\n" +
                            "where pc.post_id = ? order by c.time asc";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                commentList.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
            return commentList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Comment> getReplies(int comment_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Comment> commentList = new ArrayList();
        try
        {
            String query =  "select u.username, c.content, to_char(c.time, 'dd-mm-yyyy') || ' at ' || to_char(c.time, 'hh:mi am') time, c.comment_id\n" +
                            "from users u join comments c on u.user_id = c.user_id\n" +
                            "join comment_reply cr on c.comment_id = cr.reply_id\n" +
                            "where cr.comment_id = ? order by c.time asc";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, comment_id);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                commentList.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
            return commentList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
    public void addComment(int post_id, int user_id, String content)
    {
        CallableStatement stmt = null;
        try
        {
            String exeCommand = "begin add_post_comment(?, ?, ?); end;";
            stmt = conn.prepareCall(exeCommand);
            stmt.setInt(1, post_id);
            stmt.setInt(2, user_id);
            stmt.setString(3, content);
            stmt.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addReply(int comment_id, int user_id, String content)
    {
        CallableStatement stmt = null;
        try
        {
            String exeCommand = "begin add_comment_reply(?, ?, ?); end;";
            stmt = conn.prepareCall(exeCommand);
            stmt.setInt(1, comment_id);
            stmt.setInt(2, user_id);
            stmt.setString(3, content);
            stmt.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addVote(int post_id, int user_id, int vote)
    {
        CallableStatement stmt = null;
        try
        {
            String exeCommand = "begin add_post_vote(?, ?, ?); end;";
            stmt = conn.prepareCall(exeCommand);
            stmt.setInt(1, post_id);
            stmt.setInt(2, user_id);
            stmt.setInt(3, vote);
            stmt.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addCommentVote(int comment_id, int user_id, int vote)
    {
        CallableStatement stmt = null;
        try
        {
            String exeCommand = "begin add_comment_vote(?, ?, ?); end;";
            stmt = conn.prepareCall(exeCommand);
            stmt.setInt(1, comment_id);
            stmt.setInt(2, user_id);
            stmt.setInt(3, vote);
            stmt.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
//    public int getCommentPostID(int comment_id)
//    {
//        try
//        {
//            String query = "select post_id from post_comment where comment_id = ?";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setInt(1, comment_id);
//            ResultSet rs = stmt.executeQuery();
//            if(rs.next())
//            {
//                return rs.getInt(1);
//            }
//            return 0;
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    
    public Vote getVote(int post_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            int upvote = 0, downvote = 0;
            String query = "select count(vote) from post_votes where vote > 0 and post_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, post_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Vote getCommentVote(int comment_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            int upvote = 0, downvote = 0;
            String query = "select count(vote) from comment_votes where vote > 0 and comment_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, comment_id);
            rs = stmt.executeQuery();
            if(rs.next()) upvote = rs.getInt(1);
            
            query = "select count(vote) from comment_votes where vote < 0 and comment_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, comment_id);
            rs = stmt.executeQuery();
            if(rs.next()) downvote = rs.getInt(1);
            
            return new Vote(upvote, downvote);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int addMessage(int sender_id, int receiver_id, String content)
    {
        PreparedStatement stmt = null;
        try
        {
            String insertCommand = "insert into messages values(message_id_seq.nextval, ?, ?, ?, SYSDATE)";
            stmt = conn.prepareStatement(insertCommand);
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Message> getMessages(int user_id, int other_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Message> messageList = new ArrayList();
        try
        {
            String query =  "select u.user_id, u.username, to_char(m.time, 'dd-mm-yyyy') || ' at ' || to_char(m.time, 'hh:mi am') time, m.content\n" +
                            "from users u join messages m on u.user_id = m.sender_id\n" +
                            "where (m.sender_id = ? and m.receiver_id = ?) or (m.sender_id = ? and m.receiver_id = ?)\n" +
                            "order by m.time";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            stmt.setInt(2, other_id);
            stmt.setInt(3, other_id);
            stmt.setInt(4, user_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public int addCategory(int user_id , int category_id)
    {
        PreparedStatement stmt = null;
        try
        {
            System.out.println(user_id + " " + category_id);
            String insertCommand = "insert into favourites values(? , ?)";
            stmt = conn.prepareStatement(insertCommand);
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int removeCategory(int user_id , int category_id)
    {
        PreparedStatement stmt = null;
        try
        {
            System.out.println(user_id + " " + category_id);
            String deleteCommand = "delete from favourites where user_id = ? and category_id = ?";
            stmt = conn.prepareStatement(deleteCommand);
            stmt.setInt(1, user_id);
            stmt.setInt(2, category_id);
            int count = stmt.executeUpdate();
            
            /*String query =  "select category_id\n" +
                            "from category\n" +
                            "where parent_category = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, category_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
               removeCategory(user_id , rs.getInt(1));
            }
            */
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
               
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    public ArrayList<Category> getFavouriteCategories(int user_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Category> favouriteCategories = new ArrayList();
        try
        {
            String query =  "select c.category_id , c.parent_category , c.category_name  " + 
                            "from favourites f, category c " +
                            "where f.user_id = ? and f.category_id = c.category_id";
            stmt = conn.prepareStatement(query);
            System.out.println("OFDebug db : " + user_id);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                favouriteCategories.add(new Category(rs.getInt(1), rs.getInt(2), rs.getString(3)));
            }
            return favouriteCategories;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return favouriteCategories;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }
    
    
    
    public int addFile(int user_id, String filename, int filesize, InputStream inputStream)
    {
        PreparedStatement stmt = null;
        if(filesize > 1000000000) return 0;
        if(inputStream == null) return 0;
        try
        {
            String insertCommand = "insert into files values(file_id_seq.nextval, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertCommand);
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public ArrayList<File> getFiles(int user_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<File> fileList = new ArrayList();
        try
        {
            String query =  "select file_id, filename, filesize from files where user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public DFile downloadFile(int file_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query =  "select filename, file_data from files where file_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, file_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int deleteFile(int file_id)
    {
        PreparedStatement stmt = null;
        try
        {
            String deleteCommand = "delete files where file_id = ?";
            stmt = conn.prepareStatement(deleteCommand);
            stmt.setInt(1, file_id);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    public ArrayList<Category> getChildCategories(int category_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Category> categories = new ArrayList();
        try
        {
            String query = "select * from category where parent_category = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, category_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean isAdmin(int user_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "select role from users where user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                return rs.getString(1).equals("admin");
            }
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int deleteUser(int delete_id)
    {
        PreparedStatement stmt = null;
        try
        {
            String deleteCommand = "delete users where user_id = ?";
            stmt = conn.prepareStatement(deleteCommand);
            stmt.setInt(1, delete_id);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.out.println("Exception on stmt.close() or rs.close");
            }
        }
    }
    
    public int deletePost(int post_id)
    {
        PreparedStatement stmt = null;
        try
        {
            String deleteCommand = "delete posts where post_id = ?";
            stmt = conn.prepareStatement(deleteCommand);
            stmt.setInt(1, post_id);
            int count = stmt.executeUpdate();
            return count;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.out.println("Exception on stmt.close() or rs.close");
            }
        }
    }
    
    public ArrayList<Integer> getSubCategory(int category_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> sub = new ArrayList();
        sub.add(category_id);
        try
        {
            String query = "select category_id from category where parent_category = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, category_id);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                sub.add(rs.getInt(1));
            }
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
            int len = sub.size();
            for(int i = 1; i < len; i++)
            {
                sub.addAll(getSubCategory(sub.get(i)));
            }
            return sub;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return sub;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println("Exception on stmt.close() or rs.close");
            }
        }
    }
    
    public ArrayList<Post> recentCategorisedPosts(int category_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> sub = getSubCategory(category_id);
        ArrayList<Post> posts = new ArrayList();
        for(int cati : sub)
        {
            try
            {
                String query =  "select post_id, user_id, content, to_char(time, 'dd-mm-yyyy') || ' at ' || to_char(time, 'hh:mi am') datetime, title " +
                                "from posts\n" +
                                "where post_id in\n" +
                                "(select post_id from post_category where category_id = ?)\n" +
                                "order by time desc";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, cati);
                rs = stmt.executeQuery();
                while(rs.next())
                {
                    posts.add(new Post(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try {
                    if(stmt != null) stmt.close();
                    if(rs != null) rs.close();
                } catch (SQLException ex) {
                    System.out.println("Exception on stmt.close() or rs.close");
                }
            }
        }
        System.out.println(posts.size());
        Set<Post> postset = new HashSet();
        postset.addAll(posts);
        posts.clear();
        System.out.println(postset.size());
        posts.addAll(postset);
        return posts;
    }
    
    public ArrayList<Post> recentfavouritePosts(int user_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList();
        try
        {
            String query =  "select post_id, user_id, content, to_char(time, 'dd-mm-yyyy') || ' at ' || to_char(time, 'hh:mi am') datetime, title \n" +
                            "from posts \n" +
                            "where post_id in (select POST_ID\n" +
                            "from post_category\n" +
                            "where category_id in (select category_id\n" +
                            "from favourites\n" +
                            "where user_id = ?))\n" +                           
                            "order by time desc";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println("Exception on stmt.close() or rs.close");
            }
        }
    }
    
    public String getPaid_status(int user_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //String paid_status;
        //System.out.println("Of debug db: " + user_id);
        try
        {
            String query = "select paid_status "
                         + "from users "
                         + "where user_id = ?";
            stmt = conn.prepareStatement(query);
            System.out.println("Of debug db: " + user_id);
            stmt.setInt(1,user_id);
            rs = stmt.executeQuery();
            System.out.println("Of debug: " + user_id);
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
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println("Exception on stmt.close() or rs.close");
            }
        }
    }
    
    public boolean isPaid(int user_id)
    {
        String status = getPaid_status(user_id);
        if(status == null || status.equals("off")) return false;
        else return true;
    }
    
    
    public Profile getProfile(int profile_id)
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query =  "select first_name, last_name, email, point "
                          + "from users where user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, profile_id);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                return (new Profile(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println("Exception on stmt.close() or rs.close");
            }
        }
    }
    
}
