package club.seedymusic.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.dao.CdDAO;

/**
 * Servlet implementation class TestDAOGet
 */
@WebServlet("/test/TestDAOGet")
public class TestDAOGet extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestDAOGet()
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
      // Create our DAO
      CdDAO cdDAO = new CdDAO();

      // The JSP form passes us the cdid as a string parameter; retrieve an conver to int
      int cdid = Integer.parseInt(request.getParameter("cdid"));

      // Retrieve the requested cd by int
      Cd cd = cdDAO.getCd(cdid);

      // Put it into the session object for the jsp
      HttpSession session = request.getSession();
      session.setAttribute("cd", cd);

      // Return to the servlet
      response.sendRedirect(request.getHeader("referer"));
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}
