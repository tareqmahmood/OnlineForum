package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import model.File;
import template.QuickLink;
import db.DataAccess;

public final class file_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");

    int user_id = 0;
    if(session.getAttribute("user_id") == null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("start.jsp");
        rd.forward(request, response);
    }
    user_id = (Integer) session.getAttribute("user_id");
    DataAccess db = DataAccess.getDataAccess(request.getSession());

      out.write("\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Manage Files</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("    ");

        out.println(QuickLink.quicklinks);
    
      out.write("\n");
      out.write("    <h2>Your Files</h2>\n");
      out.write("    <table cellspacing=\"10\">\n");
      out.write("    ");

        
        ArrayList<File> fileList = db.getFiles(user_id);
        for(File f : fileList)
        {
            out.println(String.format("<tr><td> %s </td> <td> %s </td> <td><a href='DownloadFile.do?file_id=%d'>Download</a></td> <td><a href='DeleteFile.do?file_id=%d'>Delete</a></td></tr>", f.getFilename(), f.getNormalizedFilesize(), f.getFile_id(), f.getFile_id()));
        }
    
      out.write("\n");
      out.write("    </table>\n");
      out.write("    <h2>Upload File</h2>\n");
      out.write("    <i>Careful: File size has to be under 95 MB</i><br>\n");
      out.write("    <form action=\"AddFile.do\" method=\"post\" enctype=\"multipart/form-data\">\n");
      out.write("        <p><input type=\"file\" name=\"file\" size=\"40\"/></p><br>\n");
      out.write("        <input type=\"submit\" value=\"Upload\" />\n");
      out.write("    </form>\n");
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
