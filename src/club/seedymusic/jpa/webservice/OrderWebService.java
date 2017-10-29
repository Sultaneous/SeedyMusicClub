package club.seedymusic.jpa.webservice;

import javax.jws.WebService;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.dao.AccountDAO;
import club.seedymusic.jpa.exceptions.FailedLoginException;
import club.seedymusic.jpa.exceptions.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;

@WebService(name="Order", serviceName="OrderWebService")
public class OrderWebService {
	
	AccountDAO accountDAO = new AccountDAO();
	
	/**
	 * Creates an account. If an account already exists, throw an exception for the controller servlet to catch and use
	 * to notify a user that the account already exists.
	 * @param accountName Username of the user to create.
	 * @param accountInfo Account information of the user to create.
	 * @throws UserAlreadyExistsException Thrown exception caught by a controller servletto 
	 */
	@WebMethod
	public void createAccount(String accountName, Account accountInfo) throws UserAlreadyExistsException {
		// check if account already exists by username
		if (accountDAO.getAccount(accountName) != null) {
			throw new UserAlreadyExistsException();
		} else {
			accountDAO.addAccount(accountInfo);			
		}
	}
	
	/**
	 * Checks if an account with a given username and password
	 * @param accountName Username of the user to check.
	 * @param accountPassword Password of the user to check.
	 * @param accountInfo Account info object to return with the user's actual information if the user exists. 
	 * @return The Account model containing the specified user's info if the given passwrod was correct.
	 */
	@WebMethod
	public Account getAccount(String accountName, String accountPassword, Account accountInfo) throws FailedLoginException{
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

}
