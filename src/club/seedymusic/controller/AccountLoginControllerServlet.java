package club.seedymusic.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.exceptions.FailedLoginException;
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
		Account accountInfo = new Account();
		if (orderWebService.verifyCredentials(request.getParameter("username"), request.getParameter("password"))) {
			HttpSession httpSession = request.getSession(true);
			// set max idle time
//			httpSession.setMaxInactiveInterval(arg0);
		} else {
			// notify user the login failed
		}
		
		HttpSession session = request.getSession();
        session.setAttribute("account", accountInfo);
        
        // check on how to send data back to server
		response.sendRedirect(request.getHeader("referer"));
	}

}
