package club.seedymusic.controller;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.exceptions.UserAlreadyExistsException;
import club.seedymusic.exceptions.UserDoesNotExistException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.webservice.OrderWS;

@WebServlet("/account/AccountCreateControllerServlet")
public class AccountCreateControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// credit to Jason at https://stackoverflow.com/questions/8204680/java-regex-email
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	// credit to Ravi at https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
	private static final Pattern VALID_PHONE_REGEX = 
			Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", Pattern.CASE_INSENSITIVE);
	// credit to Lokesh at https://howtodoinjava.com/regex/java-regex-validate-canadian-postal-zip-codes/
	private static final Pattern VALID_POSTAL_CODE_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private final String postalCodeRegex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";

	private OrderWS orderWebService;
	public AccountCreateControllerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		
		if (!validateInput(request, response)) {
			request.getRequestDispatcher("/create.jsp").forward(request, response);
		}

		Account accountToBeAdded = new Account();
		String accountUsername = request.getParameter("username");

		accountToBeAdded.setUsername(accountUsername);
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
			doTrustToCertificates();
			
			orderWebService.createAccount(accountUsername, accountToBeAdded);
			HttpSession session = request.getSession();
			Account accountDetails = orderWebService.getAccountDetails(accountUsername);
			session.setAttribute("userId", accountDetails.getId());
			session.setAttribute("firstName", accountDetails.getFirstName());
			session.setAttribute("lastName", accountDetails.getLastName()); 
			session.setAttribute("account", accountDetails);
			
		} catch (UserAlreadyExistsException exception) {
			request.setAttribute("userExistsError", "This user already exists.");
			request.getRequestDispatcher("/create.jsp").forward(request,  response);
		} catch (UserDoesNotExistException exception) {
			request.setAttribute("loginErrorMessage", "Account created, but an issue occured on login. Try logging in or contact us about the issue.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// check on how to send data back to server
		response.sendRedirect(request.getHeader("referer"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * called to validate the input fields. If there is anything out of place the user will 
	 * @param request
	 * @param response
	 * @return True if all fields matched regex requirements, false otherwise
	 * @throws IOException
	 * @throws ServletException 
	 */
	private boolean validateInput(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if password, email, phone number (North American) and postal code(Canadian) are valid
		String emailStr = request.getParameter("email");
		Matcher emailMatcher = VALID_PHONE_REGEX .matcher(emailStr);
		boolean emailInvalid = !(emailMatcher.find());

		String phoneStr = request.getParameter("phone");
		Matcher phoneMatcher = VALID_EMAIL_ADDRESS_REGEX .matcher(phoneStr);
		boolean phoneInvalid = !(phoneMatcher.find());

		String postalCodeStr = request.getParameter("postalCode");
		Matcher postalCodeMatcher = VALID_POSTAL_CODE_REGEX .matcher(postalCodeStr);
		boolean postalCodeInvalid = !(postalCodeMatcher.find());

		boolean passwordMismatch = request.getAttribute("passwordRetyped").equals(request.getAttribute("password"));

		boolean validInput = true;
		if (emailInvalid || phoneInvalid || postalCodeInvalid || passwordMismatch) {
			if (emailInvalid) {
				request.setAttribute("emailError", "Email format incorrect. Should be of example format: example@host.com");		
				validInput = false;
			}

			if (phoneInvalid) {
				request.setAttribute("phoneError", "Phone number format incorrect. Should be of example format: 123-456-7890, (123) 456-7890, or 123 456 7890.");
				validInput = false;
			}

			if (postalCodeInvalid) {
				request.setAttribute("postalCodeError", "Postal code format incorrect. Should be of example format:  K1A 0B1");
				validInput = false;
			}

			if (passwordMismatch) {
				request.setAttribute("passwordMismatchError", "Passwords do not match.");
				validInput = false;
			}
		}
		return validInput;
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
