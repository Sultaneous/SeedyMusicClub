package club.seedymusic.jpa.controllers;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.exceptions.UserAlreadyExistsException;
import club.seedymusic.jpa.webservice.OrderWS;

@WebServlet("/test/AccountCreateControllerServlet")
public class AccountCreateControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderWS orderWebService;
	public AccountCreateControllerServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		
		Account accountToBeAdded = new Account();
		accountToBeAdded.setUsername(request.getParameter("username"));
		accountToBeAdded.setPassword(request.getParameter("password"));
		accountToBeAdded.setFirstName(request.getParameter("firstName"));
		accountToBeAdded.setLastName(request.getParameter("lastName"));
		accountToBeAdded.setStreet(request.getParameter("street"));
		accountToBeAdded.setCity(request.getParameter("city"));
		accountToBeAdded.setProvince(request.getParameter("province"));
		accountToBeAdded.setCountry(request.getParameter("country"));
		accountToBeAdded.setPostalCode(request.getParameter("postalCode"));
		accountToBeAdded.setPhone(request.getParameter("phone"));
		accountToBeAdded.setEmail(request.getParameter("email"));

		try {
			orderWebService.createAccount(request.getParameter("username"), accountToBeAdded);
		} catch (UserAlreadyExistsException exception) {
			// DO SOME RETURN MESSAGE OF ERROR OR WHATEVER
		}
		
		// check on how to send data back to server
		response.sendRedirect(request.getHeader("referer"));
	}
}
