package club.seedymusic.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.exceptions.FailedLoginException;
import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.webservice.OrderWS;

@WebServlet("/account/AccountLoginControllerServlet")
public class AccountLoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderWS orderWebService;
	
	public AccountLoginControllerServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		
		String userToVerify = request.getParameter("username");
		
		if (orderWebService.verifyCredentials(userToVerify, request.getParameter("password"))) {
			try {
				Account accountInfo = orderWebService.getAccountDetails(userToVerify);
				HttpSession httpSession = request.getSession(true);
				// store the userId, the first and last name of the user are also stored for ease of access
				httpSession.setAttribute("userId", accountInfo.getId()); 
				httpSession.setAttribute("firstName", accountInfo.getFirstName());
				httpSession.setAttribute("lastName", accountInfo.getLastName()); 
				// set max idle time
//				httpSession.setMaxInactiveInterval(arg0);
			} catch(UserDoesNotExistException exception) {
				// redirect user to a critical failure page for login as the user somehow doesn't exist
			}
			
		} else {
			// notify user the login failed
		}
	}

}
