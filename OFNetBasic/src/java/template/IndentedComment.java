/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import db.DataAccess;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import model.Comment;

/**
 *
 * @author HP
 */
public class IndentedComment {
    
    public static void print(Comment comment, JspWriter out, HttpSession session) throws IOException
    {
        DataAccess db = (DataAccess) session.getAttribute("db");
        dfsPrint(comment, out, db, 0);
    }
    
    private static void dfsPrint(Comment c, JspWriter out, DataAccess db, int h) throws IOException
    {
        out.println(String.format("<div style='margin-left:%dcm;padding-bottom:.5cm'> "
                        + "<span style='font-size:14pt'><b> %s </b></span> "
                        + "<i>commented %s</i> <br>"
                        + "<span style='font-size:13pt'> %s </span><br>"
                        + "<span style='font-size:10pt'>"
                        + "<a href='AddCommentVote.do'>Up Vote(0)</a> - "
                        + "<a href='AddCommentVote.do'>Down Vote(0)</a> - "
                        + "<a href='#' onclick='addTextArea(%d);'>Reply</a>"
                        + "</span>"
                        + "<div id='%d'></div>"
                        + "</div>", 2 * h, c.getUsername(), c.getTime(), c.getContent(), c.getComment_id(), c.getComment_id()));
        ArrayList<Comment> replyList = db.getReplies(c.getComment_id());
        for(Comment r : replyList)
        {
            dfsPrint(r, out, db, h + 1);
        }
    }
}
