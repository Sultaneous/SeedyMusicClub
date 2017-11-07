package club.seedymusic.ecom;

import club.seedymusic.jpa.bean.*;
import club.seedymusic.webservice.*;

import java.util.ArrayList;
import java.util.List;


/*
 * CLASS IS UNDER CONSTRUCTION THIS IS A SOURCE PLACEHOLDER
 */

public class ShoppingCart
{
   
   private ArrayList<Cd> cartItems = new ArrayList<Cd>();
   
   private double orderTotal ;
   
   private int lineItemCount;
    
   
   /***
    * 
    * @return count of cds in the shopping cart
    */
   public int getLineItemCount() {
   
	   lineItemCount= cartItems.size();
	   return lineItemCount;
   }
   
  
   
   
   /**
    * 
    * @param cartItem is a cd to be added to the shopping cart. Also recalculates total
    */
   @SuppressWarnings("unchecked")
public void addCartItem(Cd cartItem) {
	   
	   //check if this item already exists
	   if(cartItems.size()==0)
	   {
		   cartItems.add(cartItem);
		    calculateOrderTotal();
	   }
	   else
	   {
		   boolean exists=false;
	   for(int i=0; i<cartItems.size();i++)
	   {
		   if(cartItems.get(i).getId()==cartItem.getId())
			   {
			     exists=true;
			   }
	   }
	     if(!exists)
	     {
	    	 cartItems.add(cartItem);
			    calculateOrderTotal();
	     }
	   }
   }
    
   
   /**
    * 
    * @param iItemIndex this is the index of the item in the shopping cart Arraylist to be removed from the cart.
    * @return the cart item at iItemIndex
    */
   public Cd getCartItem(int iItemIndex) {
    Cd cartItem = null;
    if(cartItems.size()>iItemIndex) {
     cartItem = (Cd) cartItems.get(iItemIndex);
    }
    return cartItem;
   }
    
   /**
    * 
    * @return all cart items
    */
public ArrayList<Cd> getCartItems() {
	   
    return cartItems;
   }
   
   /**
    * Sets the shopping cartItems
    * 
    * @param CartItems this is a shopping cart that might have been retrieved from a session.
    */
   public void setCartItems(ArrayList<Cd> CartItems) {
   this.cartItems = CartItems;
   }
   
   /**
    * 
    * @return the total cost of the shopping Cart
    */
   public double getOrderTotal() {
    return orderTotal;
   }
   
   /**
    * setter for dblOrderTotal property
    * 
    * @param dblOrderTotal sets the cost of the total order
    */
   public void setOrderTotal(double dblOrderTotal) {
    this.orderTotal = dblOrderTotal;
   }
    
   /**
    * Calculates the shopping cart Total cost
    */
   protected void calculateOrderTotal() {
    double dblTotal = 0;
    for(int counter=0;counter<cartItems.size();counter++) {
    	 Cd cd= cartItems.get(counter);
     dblTotal+=cd.getPrice();
      
    }
    setOrderTotal(dblTotal);
   }
   
   
   /**
    * deletes an item from the cart 
    * 
    * @param strItemIndex this is the index of the item to be deleted from the cart
    */
   public void deleteCartItem(String strItemIndex) {
	   int iItemIndex = 0;
	   try {
	    iItemIndex = Integer.parseInt(strItemIndex);
	    cartItems.remove(iItemIndex);
	    calculateOrderTotal();
	   } catch(NumberFormatException nfe) {
	    System.out.println("Error while deleting cart item: "+nfe.getMessage());
	    nfe.printStackTrace();
	   }
	  }
   

}
