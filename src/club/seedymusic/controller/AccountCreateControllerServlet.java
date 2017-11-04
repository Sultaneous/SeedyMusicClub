package club.seedymusic.controller;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import club.seedymusic.exceptions.UserAlreadyExistsException;
import club.seedymusic.jpa.bean.Account;
import club.seedymusic.webservice.OrderWS;

@WebServlet("/account/AccountCreateControllerServlet")
/**
 * @author Karim Sultan
 * Servlet implements class AccountCreateController.
 *
 */
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
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderWebService = new OrderWS();
		
		// check if password, email, phone number (North American) and postal code(Canadian) are valid and passwords match
		String emailStr = request.getParameter("email");
		Matcher emailMatcher = VALID_PHONE_REGEX .matcher(emailStr);
		boolean emailInvalid = !(emailMatcher.find());
		
		String phoneStr = request.getParameter("email");
		Matcher phoneMatcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		boolean phoneInvalid = !(phoneMatcher.find());
		
		String postalCodeStr = request.getParameter("email");
		Matcher postalCodeMatcher = VALID_POSTAL_CODE_REGEX .matcher(emailStr);
		boolean postalCodeInvalid = !(postalCodeMatcher.find());
		
		boolean passwordMismatch = request.getAttribute("passwordRetyped").equals(request.getAttribute("password"));
		
		if (emailInvalid || phoneInvalid || postalCodeInvalid || passwordMismatch) {
			if (emailInvalid) {
				request.setAttribute("emailError", "Email format incorrect. Should be of example format: example@host.com");		
			}
			
			if (phoneInvalid) {
				request.setAttribute("phoneError", "Phone number format incorrect. Should be of example format: 123-456-7890, (123) 456-7890, or 123 456 7890.");
			}
			
			if (postalCodeInvalid) {
				request.setAttribute("postalCodeError", "Postal code format incorrect. Should be of example format:  K1A 0B1");
			}
			
			if (passwordMismatch) {
				request.setAttribute("passwordMismatchError", "Passwords do not match.");
			}
			
			request.getRequestDispatcher("/create.jsp").forward(request,  response);
		}
		
		/**
		 * @param AccountToBeAdded gets parameters to be set from register.jsp.
		 */
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
		accountToBeAdded.setEmail(emailStr);

		try {
			orderWebService.createAccount(accountUsername, accountToBeAdded);
		} catch (UserAlreadyExistsException exception) {
			request.setAttribute("userExistsError", "This user already exists.");
			request.getRequestDispatcher("/create.jsp").forward(request,  response);
		}
		
		// check on how to send data back to server
		response.sendRedirect(request.getHeader("referer"));
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
