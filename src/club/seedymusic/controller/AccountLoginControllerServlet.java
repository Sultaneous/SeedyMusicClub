package club.seedymusic.controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		
		String userToVerify = request.getParameter("username");
		String userPassword = request.getParameter("password");
		try {
			doTrustToCertificates();
			
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
		} catch(Exception e) {
			e.printStackTrace();
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
	
	public static void doTrustToCertificates() throws Exception {
	    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	    TrustManager[] trustAllCerts = new TrustManager[]{
	        new X509TrustManager() {
	            @Override
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }

	            @Override
	            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	            }

	            @Override
	            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	            }
	        }
	    };

	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, new SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    HostnameVerifier hv = new HostnameVerifier() {
	        @Override
	        public boolean verify(String urlHostName, SSLSession session) {
	            if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
	               // logger.warn("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
	            }
	            return true;
	        }
	    };
	    HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
}
