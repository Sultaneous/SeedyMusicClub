package club.seedymusic.jpa.webservice;

import javax.jws.WebService;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.jpa.dao.AccountDAO;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;

@WebService
public class OrderWebService {
	
	AccountDAO accountDAO = new AccountDAO();
	
	// account info retrieval and creation
	@WebMethod
	public void createAccount(String accountName, Account accountInfo) {
		// check if account already exists by username
		// PLACEHOLDER
		Boolean accountExists = false;
		if (accountExists) {
			throw SOME EXCEPTION
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
	public Account getAccount(String accountName, String accountPassword, Account accountInfo) {
		// PLACEHOLDER IMPLEMENTATION
		List<Account> accountList = accountDAO.listAccounts();
		for (Account currentAccount: accountList) {
			if (currentAccount.getUsername().equals(accountName)) {
				if (currentAccount.getPassword().equals(accountPassword)) {
					accountInfo = currentAccount;
				} else {
					// throw an error of sorts to let the JSP display a failure to login
				}
			}
		}
		return accountInfo;
	}

}
