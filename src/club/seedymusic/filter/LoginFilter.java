package club.seedymusic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="LoginFilter", servletNames = {"OrderController"})
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Followed example by Balus
	 * https://stackoverflow.com/questions/13274279/authentication-filter-and-servlet-for-login
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login.jsp";
        
        boolean loggedIn = session != null && session.getAttribute("userId") != null;;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn) {
        	chain.doFilter(request, response);
        } else {
        	// forward with error message
        	request.setAttribute("errorMessage", "You must be logged in to do that action.");
        	request.getRequestDispatcher(loginURI).forward(request,  response);
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}