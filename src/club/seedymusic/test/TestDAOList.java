package club.seedymusic.test;

import java.io.IOException;
import java.util.List;

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
@WebServlet("/test/TestDAOList")
public class TestDAOList extends HttpServlet
{
   /**
    * The serial ID of this servlet.
    */
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestDAOList()
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
      // Determine type of lookup
      String lookup = request.getParameter("lookup");

      if (lookup.equals("cd"))
      {
         // Retrieve parameters
         String action = request.getParameter("action");
         String criteria = request.getParameter("criteria");

         int pageStart;
         try
         {
            pageStart = Integer.parseInt(request.getParameter("pageStart"));
         }
         catch (NumberFormatException e)
         {
            pageStart = 1;
         }

         int pageSize;
         try
         {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
         }
         catch (NumberFormatException e)
         {
            pageSize = 1;
         }

         // Create our DAO and result set
         CdDAO cdDAO = new CdDAO();
         List<Cd> cds = null;

         // Retrieve the requested cd by id
         if (action.equals("list"))
         {
            cds = cdDAO.listCds();
         }
         else if (action.equals("listPaged"))
         {
            cds = cdDAO.listCds(pageStart, pageSize);
         }
         else if (action.equals("listByGenre"))
         {
            cds = cdDAO.listCds(criteria);
         }
         else if (action.equals("listByGenrePaged"))
         {
            cds = cdDAO.listCds(criteria, pageStart, pageSize);
         }
         else if (action.equals("search"))
         {
            cds = cdDAO.searchCds(criteria);
         }
         else if (action.equals("searchPaged"))
         {
            cds = cdDAO.searchCds(criteria, pageStart, pageSize);
         }

         // Put it into the session object for the jsp
         HttpSession session = request.getSession();
         session.setAttribute("cds", cds);
      }

      // Return to the calling JSP page
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
