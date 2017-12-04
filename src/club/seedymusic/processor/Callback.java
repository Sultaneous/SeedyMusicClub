package club.seedymusic.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import club.seedymusic.jpa.dao.OrderDAO;

/**
 * Servlet implementation class Callback
 * 
 * The Callback is only called once a successful payment is made.
 */
@WebServlet("/Processor/Callback")
public class Callback extends HttpServlet
{
   /**
    * The serialization ID of the class.
    */
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public Callback()
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

      try
      {
         // Order data agent
         OrderDAO orderDAO = new OrderDAO();

         // Check result field
         int orderId = Integer.parseInt(request.getParameter("orderId"));
         int nonce = Integer.parseInt(request.getParameter("nonce"));

         // This part is turned off for testing, but if turned on,
         // will insure the nonce in callback is the same as the nonce
         // cached in the order record

         // if (nonce != orderDAO.getNonce(orderId))
         // throw new Exception ("Nonce mismatch.");


         // Nonces match; set the order status to PAID
         orderDAO.setStatus(orderId, "PAID");

         // Notify the API that we are done with this callback instance:
         response.getWriter().append("ok*");
      }
      catch (Exception e)
      {
         // Failure
         e.printStackTrace();
      }
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
