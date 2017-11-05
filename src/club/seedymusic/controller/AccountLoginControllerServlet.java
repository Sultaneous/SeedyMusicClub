package club.seedymusic.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.webservice.OrderWS;
/**
 * Servlet implements AccountLoginControllerServlet
 */
@WebServlet("/account/AccountLoginControllerServlet")
public class AccountLoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderWS orderWebService;
	
	public AccountLoginControllerServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		/**
		 * verify the username and password entered by the user matches the record and inform the user on the status of the login process
		 * @param userToVerify username entered by the user
		 * @param userPassword password entered by the user
		 * 
		 */
		String userToVerify = request.getParameter("username");
		String userPassword = request.getParameter("password");
		if (orderWebService.verifyCredentials(userToVerify, userPassword)) {
			try {
				Account accountDetails = new Account();
				accountDetails = orderWebService.getAccountDetails(userToVerify);
				HttpSession session = request.getSession(true);
				// store the userId, the first and last name of the user are also stored for ease of access
				session.setAttribute("userId", accountDetails.getId()); 
				session.setAttribute("firstName", accountDetails.getFirstName());
				session.setAttribute("lastName", accountDetails.getLastName()); 
				session.setAttribute("account", accountDetails);
				response.sendRedirect(request.getHeader("referer"));
			} catch (UserDoesNotExistException exception) {
				request.setAttribute("loginErrorMessage", "User does not exist.");
				request.getRequestDispatcher("/login.jsp").forward(request,  response);
			}
		} else {
			request.setAttribute("loginErrorMessage", "Login failed. Username/Password mismatch.");
			request.getRequestDispatcher("/login.jsp").forward(request,  response);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
