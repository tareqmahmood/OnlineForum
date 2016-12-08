package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import model.Category;
import db.DataAccess;

public final class home_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");

    int user_id = 0;
    if(session.getAttribute("user_id") == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.jsp");
        rd.forward(request, response);
    }
    user_id = (Integer) session.getAttribute("user_id");
    DataAccess db = DataAccess.getDataAccess(request.getSession());

      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Online Forum</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<a href=\"home.jsp\">home</a> \r\n");
      out.write("<a href=\"recent.jsp\">recent</a> \r\n");
      out.write("<a href=\"ShowFavourite.do\">favorites</a> \r\n");
      out.write("<a href=\"Logout.do\">logout</a> </br>\r\n");

    out.print(String.format("<h2>Welcome %s</h2>", db.getUsername(user_id)));

      out.write("\r\n");
      out.write("<p>\r\n");
      out.write("<form method=\"post\" action=\"AddPost.do\">\r\n");
      out.write("    Title <input type=\"text\" name=\"title\" /><br>\r\n");
      out.write("    Type your thoughts <br>\r\n");
      out.write("    <textarea name=\"content\" cols=\"40\" rows=\"5\"></textarea><br>\r\n");
      out.write("    ");

        ArrayList<Category> categories = db.getAllCategories();
        for(Category ctg : categories)
        {
            out.println(String.format("<input type=\"checkbox\" name=\"category\" value=\"%d\"> %s<br>", ctg.getCategory_id(), ctg.getCategory_name()));
        }
    
      out.write("\r\n");
      out.write("    <input type=\"submit\" value=\"Post\" />\r\n");
      out.write("</form>\r\n");
      out.write("</p>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
