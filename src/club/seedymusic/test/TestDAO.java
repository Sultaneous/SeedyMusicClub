package club.seedymusic.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.jpa.bean.OrderItem;
import club.seedymusic.jpa.dao.AccountDAO;
import club.seedymusic.jpa.dao.CdDAO;
import club.seedymusic.jpa.dao.OrderDAO;

/**
 * Servlet implementation class TestDAO
 */
@WebServlet("/test/TestDAO")
public class TestDAO extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestDAO()
   {
      super();
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
      PrintWriter out = response.getWriter();
      out.println("<html><head><title>DAO Test Results</title></head><body>");
      out.println("<h1>Testing DAO</h1>");
      out.println("Using tables: cds, accounts, orders, and orderitems");
      out.println(
               "<p>This test assumes Hibernate settings will drop and create the database tables.");
      out.println("<h3>Progress...</h3>");

      // Add some CDs
      Cd cd = new Cd();
      cd.setTitle("The Moon is Round");
      cd.setCover("no cover");
      cd.setGenre("Rock");
      cd.setPrice(9.99f);
      cd.setSample("no sample");
      cd.setQuantity(100);

      CdDAO cdDAO = new CdDAO();
      cdDAO.addCd(cd);
      int orderItem1 = cd.getId();

      cd = new Cd();
      cd.setTitle("Barking at the Forest");
      cd.setCover("no cover");
      cd.setGenre("Blues");
      cd.setPrice(4.99f);
      cd.setSample("no sample");
      cd.setQuantity(100);
      cdDAO.addCd(cd);
      int orderItem2 = cd.getId();

      out.println("<p>Step 1: Creating some dummy CDs...");

      out.println("<p>Listing CDs:");
      List<Cd> cds = cdDAO.listCds();

      if (cds == null)
      {
         out.println(
                  "<h4>ERROR: Request for cds came back empty.  Not a good sign.  Look into it.</h4>");
      }
      else
      {
         out.println("</br>");
         for (Iterator iterator = cds.iterator(); iterator.hasNext();)
         {
            cd = (Cd) iterator.next();
            out.println("<p>&nbsp;Title: " + cd.getTitle());
         }
      }

      // Add an account
      Account account = new Account();
      account.setUsername("user1");
      account.setPassword("password");
      account.setFirstName("Ali");
      account.setLastName("Babba");
      account.setStreet("314 Pi Street");
      account.setCity("Ottawa");
      account.setProvince("Ontario");
      account.setCountry("Canada");
      account.setPostalCode("A1B 2C3");
      account.setPhone("613-987-6543");

      AccountDAO accountDAO = new AccountDAO();
      boolean result = accountDAO.addAccount(account);

      out.println("<p>Step 2: Creating a dummy Account...");
      out.println("Succesful? " + String.valueOf(result).toUpperCase());

      // Make an order
      Order order = new Order();
      order.setAccountId(account.getId());
      order.getOrderItems().add(new OrderItem(orderItem1));
      order.getOrderItems().add(new OrderItem(orderItem2));

      OrderDAO orderDAO = new OrderDAO();
      result = orderDAO.addOrder(order);

      out.println("<p>Step 3: Creating a dummy Order with 2 OrderItems...");
      out.println("Succesful? " + String.valueOf(result).toUpperCase());

      out.println("<p>Test completed at " + new Date());
      out.println("</body></html>");
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
