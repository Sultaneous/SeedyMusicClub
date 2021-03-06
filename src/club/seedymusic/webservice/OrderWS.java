package club.seedymusic.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import club.seedymusic.exceptions.UserAlreadyExistsException;
import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.jpa.bean.OrderItem;
import club.seedymusic.jpa.dao.AccountDAO;
import club.seedymusic.jpa.dao.OrderDAO;
import club.seedymusic.wrapper.ConfirmOrderWrapper;
import club.seedymusic.wrapper.CreateAccountWrapper;
import club.seedymusic.wrapper.CreateOrderWrapper;
import club.seedymusic.wrapper.LoginWrapper;
import club.seedymusic.wrapper.StringWrapper;

@Path("order")
public class OrderWS
{

   private AccountDAO accountDAO;
   private OrderDAO   orderDAO;

   /**
    * Creates an account. If an account already exists, throw an exception for the controller
    * servlet to catch and use to notify a user that the account already exists.
    * 
    * @param accountName
    *           Username of the user to create.
    * @param accountInfo
    *           Account information of the user to create.
    * @return Returns a String to display, stating that the account has been successfully created.
    * @throws UserAlreadyExistsException
    *            Thrown exception caught by a controller servlet and used to inform the user that
    *            the user already exists in the DB
    */
   @POST
   @Path("createAccount")
   @Produces(MediaType.APPLICATION_JSON)
   public StringWrapper createAccount(String msg)
   {
      accountDAO = new AccountDAO();

      // remap JSON string to object
      CreateAccountWrapper createAccountWrapper = null;
      try
      {
         ObjectMapper objectMapper = new ObjectMapper();
         createAccountWrapper = (objectMapper.readValue(msg, CreateAccountWrapper.class));
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      String accountName = createAccountWrapper.getAccountName();
      Account accountInfo = createAccountWrapper.getAccountInfo();

      String accountCreationStatus = "Account created successfully.";
      // check if account already exists by username
      if (accountDAO.getAccount(accountName) != null)
      {
         accountCreationStatus = "Account Exists";
      }
      else
      {
         accountDAO.addAccount(accountInfo);
      }
      StringWrapper stringWrapper = new StringWrapper();
      stringWrapper.setStringMessage(accountCreationStatus);
      return stringWrapper;
   }

   /**
    * Checks if an account with a given username and password. If it does, retrieves all details
    * about the account including the password.
    * 
    * @param accountName
    *           Username of the user to check.
    * @param accountPassword
    *           Password of the user to check.
    * @param accountInfo
    *           Account info object to return with the user's actual information if the user exists.
    * @return The Account model containing the specified user's info if the given password was
    *         correct.
    * @throws FailedLoginException
    *            Throws an exception caught by a controller servlet and used to inform the user that
    *            the login details were wrong
    */
   /*
    * @POST
    * 
    * @Path("getAccount")
    * 
    * @Produces(MediaType.APPLICATION_JSON) public Account getAccount(String accountName, String
    * accountPassword, Account accountInfo) throws UserDoesNotExistException, FailedLoginException {
    * accountDAO = new AccountDAO(); Account accountToCheck = accountDAO.getAccount(accountName); if
    * (accountToCheck != null) { if (accountToCheck.getPassword().equals(accountPassword)) {
    * accountInfo = accountToCheck; } else { throw new FailedLoginException(); } } else { throw new
    * UserDoesNotExistException(); } return accountInfo; }
    */

   /**
    * Verifies the user's account login information is correct. If the account does not exist, users
    * should avoid being told that the account doesn't exist for security reasons.
    * 
    * @param accountName
    *           Name of the user to login as.
    * @param accountPassword
    *           Password of the user to login as.
    * @return True if user has the correct login details, false if the user entered invalid
    *         information.
    */
   @POST
   @Path("verifyCredentials")
   @Produces(MediaType.APPLICATION_JSON)
   public Boolean verifyCredentials(String msg)
   {
      accountDAO = new AccountDAO();
      // remap JSON string to object
      LoginWrapper loginWrapper = null;
      try
      {
         ObjectMapper objectMapper = new ObjectMapper();
         loginWrapper = (objectMapper.readValue(msg, LoginWrapper.class));
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      // get info
      String accountName = loginWrapper.getAccountName();
      String accountPassword = loginWrapper.getAccountPassword();

      boolean accountLoginValid = false;
      Account accountToCheck = accountDAO.getAccount(accountName);
      // user should exist first of all and the user's password should be checked after
      if (accountToCheck != null && accountToCheck.getPassword().equals(accountPassword))
      {
         accountLoginValid = true;
      }
      return accountLoginValid;
   }

   /**
    * Returns account details, with the exception of the account password.
    * 
    * @param accountName
    *           Name of the account to get details from.
    * @return Account info without the password.
    * @throws UserDoesNotExistException
    *            Notify the user that the user being checked for details does not exist.
    */
   @GET
   @Path("getAccountDetailsById")
   @Produces(MediaType.APPLICATION_JSON)
   public Account getAccountDetailsById(@QueryParam("userId") String userId)
            throws UserDoesNotExistException
   {
      accountDAO = new AccountDAO();
      int userIdInt = Integer.parseInt(userId);
      Account accountInfo = accountDAO.getAccount(userIdInt);
      if (accountInfo != null)
      {
         // don't send the password for security reasons
         accountInfo.setPassword(null);
      }
      else
      {
         return null;
      }
      return accountInfo;
   }

   /**
    * Returns account details, with the exception of the account password.
    * 
    * @param accountName
    *           Name of the account to get details from.
    * @return Account info without the password.
    * @throws UserDoesNotExistException
    *            Notify the user that the user being checked for details does not exist.
    */
   @GET
   @Path("getAccountDetails")
   @Produces(MediaType.APPLICATION_JSON)
   public Account getAccountDetails(@QueryParam("userName") String userName)
            throws UserDoesNotExistException
   {
      accountDAO = new AccountDAO();
      Account accountInfo = accountDAO.getAccount(userName);
      if (accountInfo != null)
      {
         // don't send the password for security reasons
         accountInfo.setPassword(null);
      }
      else
      {
         return null;
      }
      return accountInfo;
   }

   /**
    * Returns an order object to be persisted as the user's active order.
    * 
    * @param shoppingCartInfo
    *           Shopping cart of the user to use to make an order
    * @param shippingInfo
    *           shipping information of the user. Retrievable as long as we keep the userId of the
    *           user to ship to.
    * @return Order object to be persisted, or null if the order fails to be created.
    */
   @POST
   @Path("createOrder")
   @Produces(MediaType.APPLICATION_JSON)
   public Order createOrder(String wrapper)
   {

      ObjectMapper objectMapper = new ObjectMapper();
      CreateOrderWrapper createOrderWrapper = null;
      try
      {
         createOrderWrapper = objectMapper.readValue(wrapper, CreateOrderWrapper.class);
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      if (createOrderWrapper != null)
      {

         ArrayList<Cd> shoppingCartCds = createOrderWrapper.getShoppingCartInfo().getCartItems();


         Order order = new Order();
         order.setAccountId(createOrderWrapper.getShippingInfo().getId());

         // Changed for Part 3 (KAS)
         order.setStatus("OPEN");

         // Set<OrderItem> orderItems = new HashSet<OrderItem>();
         for (Cd currentCd : shoppingCartCds)
         {
            OrderItem currentOrder = new OrderItem();
            currentOrder.setCdid(currentCd.getId());
            // orderItems.add(currentOrder);
            order.getOrderItems().add(currentOrder);
         }
         OrderDAO orderDAO = new OrderDAO();
         boolean successfullOrder = orderDAO.addOrder(order);

         // get most recent order based on ID
         Order mostRecentOrder = null;
         for (Order currentOrder : orderDAO.listOrders())
         {
            // get initial mostRecentOrder
            if (currentOrder.getAccountId() == createOrderWrapper.getShippingInfo().getId())
            {
               if (mostRecentOrder == null)
               {
                  mostRecentOrder = currentOrder;
               }
               else if (currentOrder.getId() > mostRecentOrder.getId())
               {
                  mostRecentOrder = currentOrder;
               }
            }
         }

         return mostRecentOrder;
      }

      return null;

   }

   /**
    * Verifies that the userId of the shipping info and the purchase order match up. Sets the status
    * of the order to either paid or credit card declined.
    * 
    * @param purchaseOrder
    *           Information for the user's current active order.
    * @param shippingInfo
    *           Information for the account to ship to.
    * @param paymentInfo
    *           Credit card number, though it is not used for now.
    * @return True if the order is succesfully made, false if it isn't.
    */
   @POST
   @Path("confirmOrder")
   @Produces(MediaType.APPLICATION_JSON)
   public boolean confirmOrder(String wrapper)
   {
      orderDAO = new OrderDAO();
      ConfirmOrderWrapper confirmOrderWrapper = null;

      boolean orderCorrect = false;

      ObjectMapper objectMapper = new ObjectMapper();

      try
      {
         confirmOrderWrapper = objectMapper.readValue(wrapper, ConfirmOrderWrapper.class);
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      if (confirmOrderWrapper != null)
      {
         int confirmOrderAccountId = confirmOrderWrapper.getPurchaseOrder().getAccountId();
         int orderId = confirmOrderWrapper.getPurchaseOrder().getId();
         // either the account ID of the order and shipping info ID do not match, or we are
         // rejecting every 5th order
         boolean isFithOrder = ((orderId % 5) == 0);
         boolean orderIdNotShippingInfoId = (confirmOrderAccountId != confirmOrderWrapper
                  .getShippingInfo().getId());

         // Old for Part 1 removed in Part 3 (KAS)
         /*
          * if (orderIdNotShippingInfoId || isFithOrder) { orderDAO.setStatus(orderId, "declined");
          * } else { orderDAO.setStatus(orderId, "paid"); orderCorrect = true; }
          */

         // Bitcoin wil alert us when successful; until then use pending (KAS)
         orderDAO.setStatus(orderId, "PENDING");
      }
      return orderCorrect;
   }

   @POST
   @Path("orderList")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Order> orderList(String msg)
   {
      orderDAO = new OrderDAO();

      // do order list update call to python
      List<Order> order = orderDAO.listOrders(Integer.parseInt(msg));

      return order;
   }
}
