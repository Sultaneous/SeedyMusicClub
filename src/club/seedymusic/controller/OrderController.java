package club.seedymusic.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import club.seedymusic.ecom.ShoppingCart;
import club.seedymusic.exceptions.ProcessorException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.processor.Processor;
import club.seedymusic.wrapper.ConfirmOrderWrapper;
import club.seedymusic.wrapper.CreateOrderWrapper;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public OrderController()
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


      HttpSession session = request.getSession();
      ShoppingCart cartBean = null;

      // SC in session
      Object objCartBean = session.getAttribute("cart");
      if (objCartBean != null)
      {
         cartBean = (ShoppingCart) objCartBean;
      }

      // get base Url
      String url = request.getRequestURL().toString();
      String baseUrl = url.substring(0, url.length() - request.getRequestURI().length())
               + request.getContextPath() + "/";

      Object userId = session.getAttribute("userId");

      Account acc = getAccountDetails(baseUrl, userId.toString());

      if (acc != null)
      {

         Order order = createOrder(baseUrl, cartBean, acc);

         if (order != null)
         {
            session.setAttribute("order", order);

            // KAS Processor Prototype
            // Get a new bitcoin payment address and stash in session
            String paymentAddress;
            try
            {
               paymentAddress = Processor.getPaymentAddress(order.getId());
               session.setAttribute("order_address", paymentAddress);
            }
            catch (ProcessorException e)
            {
               e.printStackTrace();
            }

            // redirect to confirmOrder.jsp
            response.sendRedirect("confirmOrder.jsp");

         }
         else
         {
            response.getWriter().append("failed").append(request.getContextPath());

         }
      }


      response.getWriter().append("Served at lemon");
      // TODO Auto-generated method stub
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {

      HttpSession session = request.getSession();

      // this is were cc info are posted
      Object ccInfo = request.getParameter("ccInfo");
      // get order from session
      Object order = session.getAttribute("order");

      int cntr = -1;
      Object timesCreditCardUsed = session.getAttribute("timesCreditCardUsed");

      if (timesCreditCardUsed != null)
      {
         cntr = Integer.parseInt(timesCreditCardUsed.toString());
         cntr++;
         session.setAttribute("timesCreditCardUsed", cntr);
      }
      else
      {
         cntr = 1;
         session.setAttribute("timesCreditCardUsed", cntr);
      }



      // get user Id
      Object userId = session.getAttribute("userId");

      String url = request.getRequestURL().toString();
      String baseUrl = url.substring(0, url.length() - request.getRequestURI().length())
               + request.getContextPath() + "/";

      Account acc = null;
      try
      {

         acc = getAccountDetails(baseUrl, userId.toString());

      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      String orderStatus = "";
      // accept cc info and confirm order
      if (acc != null)
      {
         int accountId = acc.getId();

         if (confirmOrder(baseUrl, (Order) order, acc, (String) ccInfo))
         {
            orderStatus = "Confirmed";
            request.getSession().removeAttribute("cart");
         }
         else
         {
            orderStatus = "Credit Card declined";
         }

      }
      else
      {
         // return failed to confirm order
         orderStatus = "Failed to confirm Order";
      }

      session.setAttribute("orderStatus", orderStatus);

      response.sendRedirect(baseUrl + "orderStatus.jsp");



      // TODO Auto-generated method stub
      // doGet(request, response);
   }

   public Order createOrder(String baseUrl, ShoppingCart shoppingCart, Account acc)
   {

      Order order = null;

      URL url;
      try
      {

         url = new URL(baseUrl + "rest/order/createOrder/");

         // bypassing ssl
         doTrustToCertificates();

         // prepare data

         // create wrapper class instance
         CreateOrderWrapper createOrderWrapper = new CreateOrderWrapper();
         createOrderWrapper.setShoppingCartInfo(shoppingCart);
         createOrderWrapper.setShippingInfo(acc);


         ObjectMapper objectMapper = new ObjectMapper();

         // map wrapper class to a JSON string
         String createOrderWrapperJSON = objectMapper.writeValueAsString(createOrderWrapper);

         HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
         con.setDoOutput(true);
         con.setDoInput(true);
         con.setRequestProperty("Content-Type", "application/json");
         con.setRequestProperty("Accept", "application/json");
         con.setRequestMethod("POST");

         OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
         // write the wrapper JSON string
         wr.write(createOrderWrapperJSON);
         wr.flush();

         // int HttpsResult = con.getResponseCode();
         // if (HttpsResult == HttpsURLConnection.HTTP_OK)

         BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

         String result = "";

         String input = "";

         while ((input = reader.readLine()) != null)
         {
            result += input;
         }


         // ObjectMapper objectMapper= new ObjectMapper();

         order = objectMapper.readValue(result, Order.class);
      }
      catch (Exception e)
      {

      }
      return order;

   }

   public Account getAccountDetails(String baseUrl, String userId)
   {
      Account acc = null;

      URL url;
      try
      {

         // bypassing ssl
         doTrustToCertificates();

         url = new URL(baseUrl + "rest/order/getAccountDetailsById?userId=" + userId);

         HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
         BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

         String result = "";

         String input = "";

         while ((input = reader.readLine()) != null)
         {
            result += input;
         }


         ObjectMapper objectMapper = new ObjectMapper();

         acc = objectMapper.readValue(result, Account.class);
      }
      catch (Exception e)
      {


      }

      return acc;
   }

   public boolean confirmOrder(String baseUrl, Order order, Account acc, String cc)
   {
      URL url;
      boolean orderCorrect = false;
      try
      {

         url = new URL(baseUrl + "rest/order/confirmOrder/");

         // bypassing ssl
         doTrustToCertificates();

         // prepare data

         // create wrapper class instance
         ConfirmOrderWrapper confirmOrderWrapper = new ConfirmOrderWrapper();
         confirmOrderWrapper.setPaymentInfo(cc);
         confirmOrderWrapper.setPurchaseOrder(order);
         confirmOrderWrapper.setShippingInfo(acc);

         ObjectMapper objectMapper = new ObjectMapper();

         // map wrapper class to a JSON string
         String confirmOrderWrapperJSON = objectMapper.writeValueAsString(confirmOrderWrapper);

         HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
         con.setDoOutput(true);
         con.setDoInput(true);
         con.setRequestProperty("Content-Type", "application/json");
         con.setRequestProperty("Accept", "application/json");
         con.setRequestMethod("POST");

         OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
         // write the wrapper JSON string
         wr.write(confirmOrderWrapperJSON);
         wr.flush();

         // int HttpsResult = con.getResponseCode();
         // if (HttpsResult == HttpsURLConnection.HTTP_OK)

         BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

         String result = "";

         String input = "";

         while ((input = reader.readLine()) != null)
         {
            result += input;
         }


         // ObjectMapper objectMapper= new ObjectMapper();

         orderCorrect = objectMapper.readValue(result, boolean.class);
      }
      catch (Exception e)
      {

      }
      return orderCorrect;

   }

   boolean tryParseInt(String value)
   {
      try
      {
         Integer.parseInt(value);
         return true;
      }
      catch (NumberFormatException e)
      {
         return false;
      }
   }


   public static void doTrustToCertificates() throws Exception
   {
      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      TrustManager[] trustAllCerts = new TrustManager[]
      {
               new X509TrustManager()
               {
                  @Override
                  public X509Certificate[] getAcceptedIssuers()
                  {
                     return null;
                  }

                  @Override
                  public void checkServerTrusted(X509Certificate[] certs, String authType)
                           throws CertificateException
                  {
                  }

                  @Override
                  public void checkClientTrusted(X509Certificate[] certs, String authType)
                           throws CertificateException
                  {
                  }
               }
      };

      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      HostnameVerifier hv = new HostnameVerifier()
      {
         @Override
         public boolean verify(String urlHostName, SSLSession session)
         {
            if (!urlHostName.equalsIgnoreCase(session.getPeerHost()))
            {
               // logger.warn("Warning: URL host '" + urlHostName + "' is different to SSLSession
               // host '" + session.getPeerHost() + "'.");
            }
            return true;
         }
      };
      HttpsURLConnection.setDefaultHostnameVerifier(hv);
   }


}
