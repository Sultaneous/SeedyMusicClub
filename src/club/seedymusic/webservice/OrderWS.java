package club.seedymusic.webservice;

import javax.jws.WebService;

import club.seedymusic.ecom.ShoppingCart;
import club.seedymusic.exceptions.FailedLoginException;
import club.seedymusic.exceptions.UserAlreadyExistsException;
import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.bean.Order;
import club.seedymusic.jpa.dao.AccountDAO;
import club.seedymusic.jpa.dao.OrderDAO;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;

@WebService(name="Order", serviceName="OrderWebService")
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
	@WebMethod
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
	@WebMethod
	public Account getAccount(String accountName, String accountPassword, Account accountInfo) throws FailedLoginException{
		accountDAO = new AccountDAO();
		Account accountToCheck = accountDAO.getAccount(accountName);
		if (accountToCheck != null) {
			if (accountToCheck.getPassword().equals(accountPassword)) {
				accountInfo = accountToCheck;
			} else {
				throw new FailedLoginException();
			}
		} else {
			throw new FailedLoginException();
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
	@WebMethod
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
	@WebMethod
	public Account getAccountDetails(String accountName) throws UserDoesNotExistException{
		accountDAO = new AccountDAO();
		Account accountInfo = accountDAO.getAccount(accountName);
		if (accountInfo != null) {
			// don't send the password for security reasons
			accountInfo.setPassword(null);
		} else {
			throw new UserDoesNotExistException();
		}
		return accountInfo;
	}
	
	@WebMethod
	public boolean createOrder(ShoppingCart shoppingCartInfo, Account shippingInfo) {
		orderDAO = new OrderDAO();
		
		Order order = new Order();
		return orderDAO.addOrder(order);
	}
	
	@WebMethod
	public boolean confirmOrder(int purchaseOrder, Account shippingInfo, String paymentInfo) {
		orderDAO = new OrderDAO();
		boolean orderCorrect = false;
		
		return orderCorrect;
	}
}
