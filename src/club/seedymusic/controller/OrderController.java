package club.seedymusic.controller;

import club.seedymusic.webservice.*;
import club.seedymusic.jpa.bean.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.seedymusic.ecom.ShoppingCart;
import club.seedymusic.exceptions.UserDoesNotExistException;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		 HttpSession session = request.getSession();
		   ShoppingCart cartBean = null;

		   //SC in session 
		   Object objCartBean = session.getAttribute("cart");
		   if(objCartBean!=null) {
		    cartBean = (ShoppingCart) objCartBean ;
		   }
		
            //createOrder 
		    OrderWS orderWS= new OrderWS();
		    
		    Object userId = session.getAttribute("userId");
		    
		    //if userId is null redirect to login page with returnUrl to this action
		    
		   
		    
		    Account acc=null;
			try {
				acc = orderWS.getAccountDetails(Integer.parseInt(userId.toString()));
			} catch (UserDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if(acc!=null)
		    {
		    Order order= orderWS.createOrder(cartBean, acc);
			
		    
		    if(order!=null)
		    {
		       session.setAttribute("order",order );
		    
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 HttpSession session = request.getSession();
		
		 //this is were cc info are posted 
		Object ccInfo=request.getParameter("ccInfo");
		//get order from session 
		Object order= session.getAttribute("order");
		
		int cntr=-1;
		Object	tempCntr =session.getAttribute("cntr");
		
		if(tempCntr!=null)
		{
		  cntr=Integer.parseInt(tempCntr.toString());
		  cntr++;
		  session.setAttribute("cntr", cntr);
		}else
		{
			cntr=1;
		session.setAttribute("cntr", cntr);
		}
		
		//confirm Order every fifth time
		if (cntr==5)
		{
			//reject cc info
		}
		else {

		    OrderWS orderWS= new OrderWS();
			
			//get user Id
			Object userId= session.getAttribute("userId");
		    
		    Account acc=null;
			try {
				acc = orderWS.getAccountDetails(Integer.parseInt((userId.toString())));
			} catch (UserDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String orderStatus="";
		       //accept cc info and confirm order
					if(acc!=null) {
						// cause a decline on 5th use of credit card
						int timesCreditCardUsed = Integer.parseInt(session.getAttribute("timesCreditCardUsed").toString()) + 1;
						
						int accountId = acc.getId();
						session.setAttribute("timesCreditCardUsed", timesCreditCardUsed );
						
						if (timesCreditCardUsed == 5) {
							acc.setId(-1);
						}
						
						if (orderWS.confirmOrder((Order)order, acc,(String)ccInfo)) {
							orderStatus = "Confirmed";
						} else {
							orderStatus = "Credit Card declined";
							if (timesCreditCardUsed == 5) {
								acc.setId(accountId);
							}
						}
						
		    			} else {
		    				//return failed to confirm order
		    				orderStatus = "Failed to confirm Order";
		    			}
					
    				session.setAttribute("orderStatus", orderStatus);

    		     	response.sendRedirect("orderStatus.jsp");

					
		}
		
		
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	public Order CreateOrder(String baseUrl)
	{
		
		/*
		Order temp= new Order();
		return temp;
		
		
	      		   
	      
		    //String url = request.getRequestURL().toString();
			//String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
	
	     
	       
	      URL url;
			try {
		
			url= new URL(baseUrl + "rest/catalog/cd/"+ Integer.toString(cdID));
		
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
    
    
    //ObjectMapper objectMapper= new ObjectMapper();
    
    //Cd cd= objectMapper.readValue(result, new TypeReference<Cd>() {});
    //return cd;
	}
	catch(Exception e)
	{
		
	}
	return null;
	    		*/
		return null;
	}
	
	
	
	
	public static void doTrustToCertificates() throws Exception {
	    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	    TrustManager[] trustAllCerts = new TrustManager[]{
	        new X509TrustManager() {
	            @Override
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }

	            @Override
	            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	            }

	            @Override
	            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	            }
	        }
	    };

	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, new SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    HostnameVerifier hv = new HostnameVerifier() {
	        @Override
	        public boolean verify(String urlHostName, SSLSession session) {
	            if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
	               // logger.warn("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
	            }
	            return true;
	        }
	    };
	    HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
	
	
}
