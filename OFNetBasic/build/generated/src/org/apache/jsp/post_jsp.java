package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import model.Post;
import db.DataAccess;

public final class post_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");

    String username = (String)session.getAttribute("username");
    //System.out.println("Hello " + username);
    if(username == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.html");
        rd.forward(request, response);
    }
    int post_id = Integer.parseInt(request.getParameter("post_id"));
    DataAccess db = DataAccess.getDataAccess(request.getSession());
    Post post = db.getPost(post_id);
    String postUser = DataAccess.getDataAccess(request.getSession()).getUsername(post.getUser_id());

      out.write("\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        ");

            out.println("<title>" + post.getTitle() +"</title>"); 
        
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <a href=\"home.jsp\">home</a> \n");
      out.write("        <a href=\"recent.jsp\">recent</a> \n");
      out.write("        <a href=\"ShowFavourite.do\">favorites</a> \n");
      out.write("        <a href=\"Logout.do\">logout</a> </br>\n");
      out.write("        ");

            out.println("<h1>" + post.getTitle() + "</h1>");
            out.println("<h2> by <i>" + postUser + "</i></h2>");
            out.println("<p>" + post.getContent() + "</p>");
        
      out.write("\n");
      out.write("        <h3>Comments</h3>\n");
      out.write("        <p>\n");
      out.write("            <form method=\"post\" action=\"AddComment.do\">\n");
      out.write("                Add your comment<br>\n");
      out.write("                <textarea name=\"content\" cols=\"40\" rows=\"2\"></textarea><br>\n");
      out.write("                <input type=\"submit\" value=\"Comment\" />\n");
      out.write("            </form>\n");
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
