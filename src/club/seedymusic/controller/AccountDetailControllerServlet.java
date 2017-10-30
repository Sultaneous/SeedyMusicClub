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

@WebServlet("/account/AccountDetailControllerServlet")
public class AccountDetailControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderWS orderWebService;
	
	public AccountDetailControllerServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		Account accountInfo = new Account();
		try {
			orderWebService.getAccountDetails(request.getParameter("username"));
		} catch (UserDoesNotExistException exception) {
			response.sendRedirect("loginFailure.jsp");
		}
        request.setAttribute("account", accountInfo);
	}
}
