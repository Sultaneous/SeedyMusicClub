package club.seedymusic.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
   /**
    * Serial version ID for servlet.
    */
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestDAO()
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
      boolean result;
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
      cd.setBand("Lunar Pilots");
      cd.setCover("no cover");
      cd.setGenre("rock");
      cd.setPrice(9.99);
      cd.setSample("no sample");
      cd.setQuantity(100);

      CdDAO cdDAO = new CdDAO();
      result = cdDAO.addCd(cd);

      // Save id for order test portion
      int orderItem1 = cd.getId();

      cd = new Cd();
      cd.setTitle("Barking at the Forest");
      cd.setBand("Four Dogs");
      cd.setCover("no cover");
      cd.setGenre("blues");
      cd.setPrice(4.99);
      cd.setSample("no sample");
      cd.setQuantity(100);

      // This ternary test keeps the first result in case it was false
      // and only allows success if both add operations were true
      result = cdDAO.addCd(cd) ? result : false;

      // Save id for order test portion
      int orderItem2 = cd.getId();

      out.println("<p>Step 1: Creating some dummy CDs...");
      out.println("Succesful? " + String.valueOf(result).toUpperCase());

      out.println("<p>Listing CDs:");
      List<Cd> cds = cdDAO.listCds();

      if (cds == null)
      {
         out.println(
                  "<h4>ERROR: Request for cds came back empty.  Not a good sign.  Look into it.</h4>");
      }
      else
      {
         out.println("</br><table cellpadding=2 cellspacing=2 border=2>");
         out.println("<th>ID</th>");
         out.println("<th>Title</th>");
         out.println("<th>Band</th>");
         out.println("<th>Genre</th>");
         out.println("<th>Price</th>");
         out.println("<th>Quantity</th>");
         out.println("<th>Cover</th>");
         out.println("<th>Sample</th>");
         out.println("<th>Date</th>");

         for (Iterator<Cd> iterator = cds.iterator(); iterator.hasNext();)
         {
            cd = iterator.next();
            out.println("<tr>");
            out.println("<td>" + cd.getId() + "</td>");
            out.println("<td>" + cd.getTitle() + "</td>");
            out.println("<td>" + cd.getBand() + "</cd>");
            out.println("<td>" + cd.getGenre() + "</td>");
            out.println("<td>" + cd.getPrice() + "</td>");
            out.println("<td>" + cd.getQuantity() + "</td>");
            out.println("<td>" + cd.getCover() + "</td>");
            out.println("<td>" + cd.getSample() + "</td>");
            out.println("<td>" + cd.getDate() + "</td>");
            out.println("</tr>");
         }

         out.println("</table>");
      }

      // Add an account
      Account account = new Account();
      account.setUsername("simplesimon");
      account.setPassword("password");
      account.setFirstName("Simple");
      account.setLastName("Simon");
      account.setStreet("314 Pi Street");
      account.setCity("Ottawa");
      account.setProvince("Ontario");
      account.setCountry("Canada");
      account.setPostalCode("A1B 2C3");
      account.setPhone("613-987-6543");
      account.setEmail("simplesimon@thepieman.ca");

      AccountDAO accountDAO = new AccountDAO();
      result = accountDAO.addAccount(account);

      account = new Account();
      account.setUsername("alibaba");
      account.setPassword("aAS3Jf4");
      account.setFirstName("Ali");
      account.setLastName("Babba");
      account.setStreet("8 Cyber Road");
      account.setCity("Beijing");
      account.setProvince("Hebei");
      account.setCountry("China");
      account.setPostalCode("100000");
      account.setPhone("172-6482-1443");
      account.setEmail("ali_babs7@kaching.cn");

      // This ternary test keeps the first result in case it was false
      // and only allows success if both add operations were true
      result = accountDAO.addAccount(account) ? result : false;

      out.println("<p>Step 2: Creating 2 dummy Accounts...");
      out.println("Succesful? " + String.valueOf(result).toUpperCase());

      out.println("<p>Listing Accounts:");
      List<Account> accounts = accountDAO.listAccounts();

      if (accounts == null)
      {
         out.println(
                  "<h4>ERROR: Request for accounts came back empty.  Not a good sign.  Look into it.</h4>");
      }
      else
      {
         out.println("</br><table cellpadding=2 cellspacing=2 border=2>");
         out.println("<th>ID</th>");
         out.println("<th>First Name</th>");
         out.println("<th>Last Name</th>");
         out.println("<th>Username</th>");
         out.println("<th>Password</th>");
         out.println("<th>Street</th>");
         out.println("<th>City</th>");
         out.println("<th>Province</th>");
         out.println("<th>Country</th>");
         out.println("<th>Postal Code</th>");
         out.println("<th>Phone</th>");
         out.println("<th>Email</th>");
         out.println("<th>Date</th>");

         for (Iterator<Account> iterator = accounts.iterator(); iterator.hasNext();)
         {
            account = iterator.next();
            out.println("<tr>");
            out.println("<td>" + account.getId() + "</td>");
            out.println("<td>" + account.getFirstName() + "</td>");
            out.println("<td>" + account.getLastName() + "</td>");
            out.println("<td>" + account.getUsername() + "</td>");
            out.println("<td>" + account.getPassword() + "</td>");
            out.println("<td>" + account.getStreet() + "</td>");
            out.println("<td>" + account.getCity() + "</td>");
            out.println("<td>" + account.getProvince() + "</td>");
            out.println("<td>" + account.getCountry() + "</td>");
            out.println("<td>" + account.getPostalCode() + "</td>");
            out.println("<td>" + account.getPhone() + "</td>");
            out.println("<td>" + account.getEmail() + "</td>");
            out.println("<td>" + account.getDate() + "</td>");
            out.println("</tr>");
         }

         out.println("</table>");
      }

      // Make an order
      Order order = new Order();
      order.setAccountId(account.getId());
      order.getOrderItems().add(new OrderItem(orderItem1));
      order.getOrderItems().add(new OrderItem(orderItem2));

      OrderDAO orderDAO = new OrderDAO();
      result = orderDAO.addOrder(order);

      out.println("<p>Step 3: Creating a dummy Order with 2 OrderItems...");
      out.println("Succesful? " + String.valueOf(result).toUpperCase());

      out.println("<p>Listing Orders:");
      List<Order> orders = orderDAO.listOrders();

      if (orders == null)
      {
         out.println(
                  "<h4>ERROR: Request for orders came back empty.  Not a good sign.  Look into it.</h4>");
      }
      else
      {
         out.println("</br><table cellpadding=2 cellspacing=2 border=2>");
         out.println("<th>ID</th>");
         out.println("<th>Account ID</th>");
         out.println("<th>CD IDs</th>");
         out.println("<th>Date</th>");

         for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();)
         {
            order = iterator.next();
            out.println("<tr>");
            out.println("<td>" + order.getId() + "</td>");
            out.println("<td>" + order.getAccountId() + "</td>");

            // Now we print all the cd ids of each ordered item into the table cell
            out.println("<td>");
            Set<OrderItem> orderItems = order.getOrderItems();
            for (Iterator<OrderItem> nestedIterator = orderItems.iterator(); nestedIterator
                     .hasNext();)
            {
               OrderItem orderItem = nestedIterator.next();
               out.println(orderItem.getCdid());

               if (nestedIterator.hasNext())
                  out.println(", ");
            }
            out.println("</td>");

            out.println("<td>" + order.getDate() + "</td>");
            out.println("</tr>");
         }

         out.println("</table>");
      }

      // Wrap-up
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
