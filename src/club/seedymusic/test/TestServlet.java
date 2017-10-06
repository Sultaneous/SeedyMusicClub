package club.seedymusic.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test/TestServlet")
public class TestServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestServlet()
   {
      super();
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
      PrintWriter out = response.getWriter();
      out.append(
               "<html><head><title>Servlet Success</title></head><body><h1>SERVLET Test Succesful</h1>");
      out.append("Your servlet configuration for Seedy Music Club is correct.");
      out.append("<p>The server date and time is: " + new java.util.Date());
      out.append("<p>You can also <a href=\"" + request.getContextPath()
               + "/test/TestJSP.jsp\">test the JSP setup</a>.</body></html>");
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
      doGet(request, response);
   }

}
