package club.seedymusic.controller;

import club.seedymusic.webservice.*;
import club.seedymusic.jpa.bean.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

}
