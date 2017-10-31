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
		String userPassword = request.getParameter("password");
		if (orderWebService.verifyCredentials(userToVerify, userPassword)) {
			try {
				Account accountInfo = new Account();
				accountInfo = orderWebService.getAccount(userToVerify, userPassword, accountInfo);
				HttpSession httpSession = request.getSession(true);
				// store the userId, the first and last name of the user are also stored for ease of access
				httpSession.setAttribute("userId", accountInfo.getId()); 
				httpSession.setAttribute("firstName", accountInfo.getFirstName());
				httpSession.setAttribute("lastName", accountInfo.getLastName()); 
				response.sendRedirect(request.getHeader("referer"));
			} catch (UserDoesNotExistException exception) {
				request.setAttribute("errorMessage", "User does not exist.");
				request.getRequestDispatcher("/login.jsp").forward(request,  response);
			} catch (FailedLoginException exception) {
				request.setAttribute("errorMessage", "Login failed. Username/Password mismatch.");
				request.getRequestDispatcher("/login.jsp").forward(request,  response);
			}
		} else {
			request.setAttribute("errorMessage", "Login failed. Username/Password mismatch.");
			request.getRequestDispatcher("/login.jsp").forward(request,  response);
		}
	}

}
