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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.jpa.bean.OrderItem;
import club.seedymusic.wrapper.CreateOrderWrapper;

/**
 * Servlet implementation class ListOrdersController
 */
@WebServlet("/ListOrdersController")
public class ListOrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListOrdersController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get value of userId
	      String id = (String) request.getSession().getAttribute("userId").toString();
	      
	      //get baseUrl
	      String url = request.getRequestURL().toString();
	      String baseUrl = url.substring(0, url.length() - request.getRequestURI().length())
	               + request.getContextPath() + "/";
		
	      //get list of orders and put them in a session
	      List<Order> orders=getOrders(baseUrl,id);
	      request.getSession().setAttribute("orders", orders);
	      
	    //
	      int ordersCount=orders.size();
	      
	      	      
	      ArrayList<String> Albums;
	      orderAlbum orderAlbumInst;
	      
	      ArrayList<orderAlbum>Orders= new ArrayList<orderAlbum>();
	      
	      for(int i=0; i< ordersCount; i++)
	      {
	    	  
	    	   orderAlbumInst=new orderAlbum();
	    	   orderAlbumInst.order=orders.get(i);
	    	   
	    	   
	    	   Albums= new ArrayList<String>();
	    	   
	    	   for (OrderItem s : orders.get(i).getOrderItems()) {
	    		   Albums.add(getCd(baseUrl,s.getCdid()).getTitle());
	    	  }
	    	   orderAlbumInst.Albums=Albums;
	    	   
	    	   	Orders.add(orderAlbumInst);
	    	  
	      }
	      
	      
	      request.getSession().setAttribute("Orders", Orders);
	      
		//redirect to listOdOrders.jsp
			RequestDispatcher dispatcher;
	      dispatcher= request.getRequestDispatcher("listOfOrders.jsp");
			dispatcher.forward(request, response);
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  /*
	//get posted value of userId
	      String id = (String) request.getSession().getAttribute("userId");
	      
	      //get baseUrl
	      String url = request.getRequestURL().toString();
	      String baseUrl = url.substring(0, url.length() - request.getRequestURI().length())
	               + request.getContextPath() + "/";
		
	      //get list of orders and put them in a session
	      request.getSession().setAttribute("orders", getOrders(baseUrl,id));
	      
	      		
		
		//redirect to listOdOrders.jsp
			RequestDispatcher dispatcher;
	      dispatcher= request.getRequestDispatcher("listOdOrders.jsp");
			dispatcher.forward(request, response);
		*/

	}
	
	
	public List<Order> getOrders(String baseUrl, String userId)
	{
		List<Order> orders = null;

	      URL url;
	      try
	      {

	         url = new URL(baseUrl + "rest/order/orderList/");

	         // bypassing ssl
	         doTrustToCertificates();

	         // prepare data

	     
	         ObjectMapper objectMapper = new ObjectMapper();


	         HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	         con.setDoOutput(true);
	         con.setDoInput(true);
	         con.setRequestProperty("Content-Type", "application/json");
	         con.setRequestProperty("Accept", "application/json");
	         con.setRequestMethod("POST");

	         OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
	         // write the wrapper JSON string
	         wr.write(userId);
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

	         orders = objectMapper.readValue(result, new TypeReference<List<Order>>() {});
	      }
	      catch (Exception e)
	      {

	      }
	      return orders;
	}
	   
	public Cd getCd(String baseUrl,int id)
	{
		URL url;
		
		  try {
				
             	
  	  		url= new URL(baseUrl + "rest/catalog/cd/"+id);

              //call the rest webservice and get json results
             
              //bypassing ssl
              doTrustToCertificates();

              HttpsURLConnection con= (HttpsURLConnection)url.openConnection();
              BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
              
              String result="";
              
              String input="";
              
              while((input =reader.readLine())!=null)
              {
              	result+=input;
              }
              
              
              ObjectMapper objectMapper= new ObjectMapper();
              
              Cd cd= objectMapper.readValue(result,Cd.class);
              
              return cd;
              
			  } catch (Exception e) {

				e.printStackTrace();

			  }
		
		  return null;
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

  
	public class orderAlbum{
		private Order order ;
		private ArrayList<String>Albums;
		
		public  Order getOrder()
		{
			return order;
		}
		
		public ArrayList<String> getAlbums()
		{
			return Albums;
		}
		
		
		public void setOrder(Order orderA)
		{
			this.order=orderA;
		}
		
		public void setAlbums(ArrayList<String> albums)
		{
			this.Albums=albums;
		}
	}
}


