package club.seedymusic.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.webservice.OrderWS;

public class AccountLogoutControllerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private OrderWS orderWebService;
	
	public AccountLogoutControllerServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// invalidate to remove all existing user data for a session
		request.getSession().invalidate();
		// redirect back to the page that referred to this
		request.getRequestDispatcher("/login.jsp").forward(request,  response);
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
