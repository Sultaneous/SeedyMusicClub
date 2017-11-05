package club.seedymusic.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import club.seedymusic.ecom.ShoppingCart;
import club.seedymusic.exceptions.FailedLoginException;
import club.seedymusic.exceptions.UserAlreadyExistsException;
import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.jpa.bean.OrderItem;
import club.seedymusic.jpa.dao.AccountDAO;
import club.seedymusic.jpa.dao.OrderDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("order")
public class OrderWS {
	
	private AccountDAO accountDAO;
	private OrderDAO orderDAO;
	/**
	 * Creates an account. If an account already exists, throw an exception for the controller servlet to catch and use
	 * to notify a user that the account already exists.
	 * @param accountName Username of the user to create.
	 * @param accountInfo Account information of the user to create.
	 * @return Returns a String to display, stating that the account has been successfully created.
	 * @throws UserAlreadyExistsException Thrown exception caught by a controller servlet and used to inform the user 
	 * that the user already exists in the DB 
	 */
	@GET
	@Path("createAccount")
	public String createAccount(String accountName, Account accountInfo) throws UserAlreadyExistsException {
		accountDAO = new AccountDAO();
		// check if account already exists by username
		if (accountDAO.getAccount(accountName) != null) {
			throw new UserAlreadyExistsException();
		} else {
			accountDAO.addAccount(accountInfo);			
		}
		return "Account created successfully.";
	}
	
	/**
	 * Checks if an account with a given username and password. If it does, retrieves all details about the
	 * account including the password.
	 * @param accountName Username of the user to check.
	 * @param accountPassword Password of the user to check.
	 * @param accountInfo Account info object to return with the user's actual information if the user exists. 
	 * @return The Account model containing the specified user's info if the given password was correct.
	 * @throws FailedLoginException Throws an exception  caught by a controller servlet and used to inform the
	 * user that the login details were wrong
	 */
	@GET
	@Path("getAccount")
	public Account getAccount(String accountName, String accountPassword, Account accountInfo) throws UserDoesNotExistException, FailedLoginException {
		accountDAO = new AccountDAO();
		Account accountToCheck = accountDAO.getAccount(accountName);
		if (accountToCheck != null) {
			if (accountToCheck.getPassword().equals(accountPassword)) {
				accountInfo = accountToCheck;
			} else {
				throw new FailedLoginException();
			}
		} else {
			throw new UserDoesNotExistException();
		}
		return accountInfo;
	}
	
	/**
	 * Verifies the user's account login information is correct. If the account does not exist, users should 
	 * avoid being told that the account doesn't exist for security reasons.
	 * @param accountName Name of the user to login as.
	 * @param accountPassword Password of the user to login as.
	 * @return True if user has the correct login details, false if the user entered invalid information.
	 */
	@GET
	@Path("verifyCredentials")
	public boolean verifyCredentials(String accountName, String accountPassword) {
		accountDAO = new AccountDAO();
		boolean accountLoginValid = false;
		Account accountToCheck = accountDAO.getAccount(accountName);
		// user should exist first of all and the user's password should be checked after
		if (accountToCheck != null && accountToCheck.getPassword().equals(accountPassword)) {
			accountLoginValid = true;
		}
		return accountLoginValid;
	}
	
	/**
	 * Returns account details, with the exception of the account password.
	 * @param accountName Name of the account to get details from.
	 * @return Account info without the password.
	 * @throws UserDoesNotExistException Notify the user that the user being checked for details does not exist.
	 */
	@GET
	@Path("getAccountDetails")
	public Account getAccountDetails(int userId) throws UserDoesNotExistException{
		accountDAO = new AccountDAO();
		Account accountInfo = accountDAO.getAccount(userId);
		if (accountInfo != null) {
			// don't send the password for security reasons
			accountInfo.setPassword(null);
		} else {
			throw new UserDoesNotExistException();
		}
		return accountInfo;
	}
	
	/**
	 * Returns account details, with the exception of the account password.
	 * @param accountName Name of the account to get details from.
	 * @return Account info without the password.
	 * @throws UserDoesNotExistException Notify the user that the user being checked for details does not exist.
	 */
	@GET
	@Path("getAccountDetails")
	public Account getAccountDetails(String userName) throws UserDoesNotExistException{
		accountDAO = new AccountDAO();
		Account accountInfo = accountDAO.getAccount(userName);
		if (accountInfo != null) {
			// don't send the password for security reasons
			accountInfo.setPassword(null);
		} else {
			throw new UserDoesNotExistException();
		}
		return accountInfo;
	}
	
	/**
	 * Returns an order object to be persisted as the user's active order.
	 * @param shoppingCartInfo Shopping cart of the user to use to make an order
	 * @param shippingInfo shipping information of the user. Retrievable as long as we keep the userId of the user to ship to.
	 * @return Order object to be persisted, or null if the order fails to be created.
	 */
	@GET
	@Path("createOrder")
	public Order createOrder(ShoppingCart shoppingCartInfo, Account shippingInfo) {
		orderDAO = new OrderDAO();
		ArrayList<Cd> shoppingCartCds = shoppingCartInfo.getCartItems();
		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		for (Cd currentCd: shoppingCartCds) {
			OrderItem currentOrder = new OrderItem();
			currentOrder.setCdid(currentCd.getId());
			orderItems.add(currentOrder);
		}
		
		Order order = new Order();
		order.setAccountId(shippingInfo.getId());
		order.setOrderItems(orderItems);
		boolean successfullOrder = orderDAO.addOrder(order);
		
		// get most recent order based on ID
		Order mostRecentOrder = null;
		for (Order currentOrder: orderDAO.listOrders()) {
			// get initial mostRecentOrder
			if (currentOrder.getAccountId() == shippingInfo.getId() && mostRecentOrder == null) {
				mostRecentOrder = currentOrder;
			} else if (currentOrder.getAccountId() == shippingInfo.getId() && (currentOrder.getId() > mostRecentOrder.getId())) {
				mostRecentOrder = currentOrder;
			}
		}
		return mostRecentOrder;
	}
	
	/**
	 * Verifies that the userId of the shipping info and the purchase order match up. Sets the status of the order to either paid or credit card declined.
	 * @param purchaseOrder Information for the user's current active order.
	 * @param shippingInfo Information for the account to ship to.
	 * @param paymentInfo Credit card number, though it is not used for now.
	 * @return True if the order is succesfully made, false if it isn't.
	 */
	@GET
	@Path("confirmOrder")
	public boolean confirmOrder(Order purchaseOrder, Account shippingInfo, String paymentInfo) {
		boolean orderCorrect = false;
		if (purchaseOrder.getAccountId() == shippingInfo.getId()) {
			purchaseOrder.setStatus("paid");
			orderCorrect = true;
		} else {
			purchaseOrder.setStatus("credit card declined");
		}
		
		return orderCorrect;
	}
}
