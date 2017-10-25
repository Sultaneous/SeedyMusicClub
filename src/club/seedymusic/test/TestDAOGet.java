package club.seedymusic.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.jpa.dao.AccountDAO;
import club.seedymusic.jpa.dao.CdDAO;
import club.seedymusic.jpa.dao.OrderDAO;

/**
 * Servlet implementation class TestDAOGet
 */
@WebServlet("/test/TestDAOGet")
public class TestDAOGet extends HttpServlet
{
   /**
    * The serial ID of this servlet.
    */
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
      // Determine type of lookup
      String lookup = request.getParameter("lookup");

      if (lookup.equals("cd"))
      {
         // Create our DAO
         CdDAO cdDAO = new CdDAO();
         cdDAO.getGenres();
         // The JSP form passes us the cdid as a string parameter; retrieve an convert to int
         int cdid = Integer.parseInt(request.getParameter("cdid"));

         // Retrieve the requested cd by id
         Cd cd = cdDAO.getCd(cdid);

         // Put it into the session object for the jsp
         HttpSession session = request.getSession();
         session.setAttribute("cd", cd);
      }
      else if (lookup.equals("account"))
      {
         // Create our DAO
         AccountDAO accountDAO = new AccountDAO();

         // The JSP form passes us the account id as a string parameter; retrieve an convert to int
         int accountid = Integer.parseInt(request.getParameter("accountid"));

         // Retrieve the requested account by id
         Account account = accountDAO.getAccount(accountid);

         // Put it into the session object for the jsp
         HttpSession session = request.getSession();
         session.setAttribute("account", account);
      }

      else if (lookup.equals("order"))
      {
         // Create our DAO
         OrderDAO orderDAO = new OrderDAO();

         // The JSP form passes us the account id as a string parameter; retrieve an convert to int
         int orderid = Integer.parseInt(request.getParameter("orderid"));

         // Retrieve the requested account by id
         Order order = orderDAO.getOrder(orderid);

         // Put it into the session object for the jsp
         HttpSession session = request.getSession();
         session.setAttribute("order", order);
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
