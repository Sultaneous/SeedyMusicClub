package club.seedymusic.controller;

import club.seedymusic.ecom.*;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.webservice.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * CLASS IS UNDER CONSTRUCTION THIS IS A SOURCE PLACEHOLDER
 */
/**
 * Servlet implements class SessionController
 */
@WebServlet("/SessionController")
public class SessionController extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public SessionController()
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
	   
      // TODO Auto-generated method stub
      response.getWriter().append("Served at lemon : ").append(request.getContextPath());
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	   
	 		String strAction = request.getParameter("action");
	 		/**
	 		 * @param strAction tracks content of shopping cart, delete and add CDs
	 		 */	 	   
	 	   
	 		  if(strAction!=null && !strAction.equals("")) {
	 		   if(strAction.equals("add")) {
	 		    addToCart(request);
	 		   }  else if (strAction.equals("delete")) {
	 		    deleteCart(request);
	 		   }
	 		  }
	 		  response.sendRedirect("shoppingCart.jsp");  
	   
	   
      // TODO Auto-generated method stub
      doGet(request, response);
   }
   
   
   protected void deleteCart(HttpServletRequest request) {
	   HttpSession session = request.getSession();
	   String strItemIndex = request.getParameter("itemIndex");
	   ShoppingCart cartBean = null;
	    
	   Object objCartBean = session.getAttribute("cart");
	   if(objCartBean!=null) {
	    cartBean = (ShoppingCart) objCartBean ;
	   } else {
	    cartBean = new ShoppingCart();
	   }
	   cartBean.deleteCartItem(strItemIndex);
	   session.setAttribute("cart", cartBean);
	  }
	  
	   
	  protected void addToCart(HttpServletRequest request) {
	   HttpSession session = request.getSession();
	   String cdId = request.getParameter("cdId");
	    
	   ShoppingCart cartBean = null;
	    
	   Object objCartBean = session.getAttribute("cart");
	  
	   if(objCartBean!=null) {
	    cartBean = (ShoppingCart) objCartBean ;
	   } else {
	    cartBean = new ShoppingCart();
	    session.setAttribute("cart", cartBean);
	   }
	    
	   //get the cd using the cdId
	   CatalogViewModel Model= new CatalogViewModel();
	
		String url = request.getRequestURL().toString();
		String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

		int idCd=-1;
		
		if(tryParseInt(cdId))
		{ 
	       idCd= Integer.parseInt(cdId);  

		}
		if(idCd!=-1)
       {
	   Cd cd= Model.getCd(baseURL, idCd);
	   cartBean.addCartItem(cd);
	   session.setAttribute("cart", cartBean);
       }
	  }
   
   
	  boolean tryParseInt(String value) {  
		     try {  
		         Integer.parseInt(value);  
		         return true;  
		      } catch (NumberFormatException e) {  
		         return false;  
		      }  
		}

}
