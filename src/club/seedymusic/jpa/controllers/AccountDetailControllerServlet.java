package club.seedymusic.jpa.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.jpa.exceptions.FailedLoginException;
import club.seedymusic.jpa.webservice.OrderWebService;

@WebServlet("/test/AccountDetailControllerServlet")
public class AccountDetailControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderWebService orderWebService;
	
	public AccountDetailControllerServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWebService();
		Account accountInfo = new Account();
		try {
			orderWebService.getAccount(request.getParameter("username"), request.getParameter("password"), accountInfo);
		} catch (FailedLoginException exception) {
			// Redirect or pop up message for failure to login
		}
		
		HttpSession session = request.getSession();
        session.setAttribute("account", accountInfo);
        
        // check on how to send data back to server
		response.sendRedirect(request.getHeader("referer"));
	}
}
