package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import model.Post;
import java.util.ArrayList;
import db.DataAccess;

public final class recent_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");

    System.out.println("Hello ");
    String username = (String)session.getAttribute("username");
    //System.out.println("Hello " + username);
    if(username == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.html");
        rd.forward(request, response);
    }

      out.write("\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Recent Posts</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <a href=\"home.jsp\">home</a> \n");
      out.write("        <a href=\"recent.jsp\">recent</a> \n");
      out.write("        <a href=\"ShowFavourite.do\">favorites</a> \n");
      out.write("        <a href=\"Logout.do\">logout</a> </br>\n");
      out.write("        <h2>Recent Posts</h2>\n");
      out.write("        <p>\n");
      out.write("        <ul>\n");
      out.write("        ");

            DataAccess db = DataAccess.getDataAccess(request.getSession());
            ArrayList<Post> posts = db.recentPosts();
            for(Post p : posts)
            {
                String puser = DataAccess.getDataAccess(request.getSession()).getUsername(p.getUser_id());
                out.println(String.format("<li> <a href = \"post.jsp?post_id=%s\">%s</a> by <b>%s</b> at %s </li>", p.getPost_id()+"", p.getTitle(), puser, p.getDatetime()));
            }
        
      out.write("\n");
      out.write("        </ul>\n");
      out.write("        </p>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
