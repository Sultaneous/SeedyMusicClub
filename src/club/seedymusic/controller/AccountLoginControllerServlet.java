package club.seedymusic.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import club.seedymusic.exceptions.UserAlreadyExistsException;
import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.webservice.OrderWS;
import club.seedymusic.wrapper.LoginWrapper;

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
			String baseUrl = getBaseURL(request);
			URL serviceUrl = new URL(baseUrl + "rest/order/verifyCredentials/");
			
			// wrap data to send to webservice
			LoginWrapper loginWrapper = new LoginWrapper();
			loginWrapper.setAccountName(userToVerify);
			loginWrapper.setAccountPassword(userPassword);
			
			// map object into a JSON string
			ObjectMapper objectMapper = new ObjectMapper();
			String createAccountString = objectMapper.writeValueAsString(loginWrapper);
			
			// prepare connection to the URL mapping of the service method
			HttpsURLConnection con = (HttpsURLConnection)serviceUrl.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			
			// send object as JSON string across connection to the service method
	        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(createAccountString);
			wr.flush();
			
			// take the response JSON string and re-map to an expected return object type (String in this case)
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String result="";
			String input="";
						
			while((input = reader.readLine())!= null)
			{
				result += input;
			}
			
			// process any errors
			Boolean loginValid = objectMapper.readValue(result, Boolean.class);
			
			if (loginValid) {
				try {
					// fetch account details now as hibernate will have automatically assigned a new ID for the user
					
					// setup URL which will do a GET against an account username
					serviceUrl = new URL(baseUrl + "rest/order/getAccountDetails?userName=" + userToVerify);
					
					// setup new connection
					con = (HttpsURLConnection)serviceUrl.openConnection();
			        
					// prepare to read the response
					reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
					result="";
					input="";
					
					while((input =reader.readLine())!=null)
			        {
			        	result += input;
			        }
					
					Account responseAccount = objectMapper.readValue(result, Account.class);
					
					if (responseAccount == null) {
						throw new UserDoesNotExistException();
					}
					
					HttpSession session = request.getSession(true);
					// store the userId, the first and last name of the user are also stored for ease of access
					session.setAttribute("userId", responseAccount.getId()); 
					session.setAttribute("firstName", responseAccount.getFirstName());
					session.setAttribute("lastName", responseAccount.getLastName()); 
					session.setAttribute("account", responseAccount);
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
	
	private String getBaseURL(HttpServletRequest request) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + 
				request.getContextPath() + "/";
		return baseUrl;
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
