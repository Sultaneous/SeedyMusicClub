package club.seedymusic.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.dao.AccountDAO;

/**
 * Servlet implementation class TestDAOGet
 * 
 * NOTE: THIS IS FOR TEST PURPOSES (AccountDAO testing).
 * 
 * NO VALIDATION, ERROR CHECKING OR EXCEPTION HANDLING IS PERFORMED. THIS IS TEST CODE NOT
 * PRODUCTION CODE.
 */
@WebServlet("/test/TestDAOAccount")
public class TestDAOAccount extends HttpServlet
{
   /**
    * The serial ID of this servlet.
    */
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestDAOAccount()
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
      String action = request.getParameter("account.action");
      if (action == null) action = "action.unknown";

      // Stash it - it is used in the postback
      HttpSession session = request.getSession();
      session.setAttribute("account.action", action);

      if (action.equals("account.list"))
      {
         // Create our DAO
         AccountDAO accountDAO = new AccountDAO();

         // Get the list
         List<Account> accounts = accountDAO.listAccounts();

         // Put it into the session object for the jsp
         session.setAttribute("accounts", accounts);
      }

      else if (action.equals("account.get"))
      {
         // Create our DAO
         AccountDAO accountDAO = new AccountDAO();
         accountDAO.listAccounts();

         // The JSP form passes us the account id as a string parameter; retrieve and convert to int
         int accountId = Integer.parseInt(request.getParameter("account.id"));

         // Retrieve the requested account by id
         Account account = accountDAO.getAccount(accountId);

         // Put it into the session object for the jsp
         session.setAttribute("account", account);
      }

      else if (action.equals("account.add"))
      {
         // Create our DAO
         AccountDAO accountDAO = new AccountDAO();

         // Create account bean
         Account account = new Account();

         // Retrieve form results and populate bean
         account.setCity(request.getParameter("account.city"));
         account.setCountry(request.getParameter("account.country"));
         account.setEmail(request.getParameter("account.email"));
         account.setFirstName(request.getParameter("account.firstName"));
         account.setLastName(request.getParameter("account.lastName"));
         account.setPassword(request.getParameter("account.password"));
         account.setPhone(request.getParameter("account.phone"));
         account.setPostalCode(request.getParameter("account.postalCode"));
         account.setProvince(request.getParameter("account.province"));
         account.setStreet(request.getParameter("account.street"));
         account.setUsername(request.getParameter("account.username"));

         // Add it
         boolean result = accountDAO.addAccount(account);

         // Put it into the session object for the jsp
         session.setAttribute("account.result", String.valueOf(result));
      }

      else if (action.equals("account.addJson"))
      {
         // Create our DAO
         AccountDAO accountDAO = new AccountDAO();

         // Create our Account object
         Account account = Account.createObject(request.getParameter("account.Json"));

         // Add the account object. If JSON inflation failed, so will this.
         boolean result = accountDAO.addAccount(account);

         // Put it into the session object for the jsp
         session.setAttribute("account.result", String.valueOf(result));
      }

      else if (action.equals("account.delete"))
      {
         // Create our DAO
         AccountDAO accountDAO = new AccountDAO();
         System.out.println("ID: [" + request.getParameter("account.id") + "]");
         // The JSP form passes us the account id as a string parameter; retrieve and convert to int
         int accountId = Integer.parseInt(request.getParameter("account.id"));

         // First three accounts are protected
         boolean result;
         if (accountId > 3)
         {
            // Delete requested id
            result = accountDAO.deleteAccount(accountId);

         }
         else
            result = false;

         // Put it into the session object for the jsp
         session.setAttribute("account.result", String.valueOf(result));
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
