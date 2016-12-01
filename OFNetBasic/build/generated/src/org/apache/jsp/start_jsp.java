package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class start_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");

    String username = (String)session.getAttribute("username");
    if(username != null)
    {
        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request, response);
    }

      out.write("\n");
      out.write("    <title>Online Forum</title>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("</head>\n");
      out.write("<h1>Online Forum</h1>\n");
      out.write("<h3>Login</h3>\n");
      out.write("<body>\n");
      out.write("<form method=\"post\" action=\"LoginProcess.do\">\n");
      out.write("    <table>\n");
      out.write("        <tr>\n");
      out.write("            <td>Username</td>\n");
      out.write("            <td><input type=\"text\" name=\"username\" /><td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>Password</td>\n");
      out.write("            <td><input type=\"password\" name=\"password\" /></td>\n");
      out.write("        </tr>\n");
      out.write("    </table>\n");
      out.write("    <input type=\"submit\" value=\"Login\" />\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("<h3>Create New Account</h3>\n");
      out.write("\n");
      out.write("<form method=\"post\" action=\"CreateAccount.do\">\n");
      out.write("    <table>\n");
      out.write("        <tr>\n");
      out.write("            <td>First Name</td>\n");
      out.write("            <td><input type=\"text\" name=\"firstName\" /><td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>Last Name</td>\n");
      out.write("            <td><input type=\"text\" name=\"lastName\" /><td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>Username</td>\n");
      out.write("            <td><input type=\"text\" name=\"username\" /><td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>Email</td>\n");
      out.write("            <td><input type=\"text\" name=\"email\" /><td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>Password</td>\n");
      out.write("            <td><input type=\"password\" name=\"password\" /></td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("    </table>\n");
      out.write("    <input type=\"submit\" value=\"Create Account\" />\n");
      out.write("</form>\n");
      out.write("</body>\n");
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
