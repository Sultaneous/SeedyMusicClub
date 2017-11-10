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

@WebFilter(urlPatterns= {"/OrderController"})
public class LoginFilter implements Filter
{

   @Override
   public void destroy()
   {
      // TODO Auto-generated method stub

   }

   /**
    * Followed example by Balus
    * https://stackoverflow.com/questions/13274279/authentication-filter-and-servlet-for-login
    */
   @Override
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
   {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;
      HttpSession session = request.getSession(false);
      String loginURI = request.getContextPath() + "/login.jsp";

      boolean loggedIn = session != null && session.getAttribute("userId") != null;
      
      if (loggedIn)
      {
         chain.doFilter(request, response);
      }
      else
      {
         // forward with error message
    	 String url = request.getRequestURL().toString();
    	 String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + 
    	 request.getContextPath() + "/";
         request.getSession().setAttribute("loginErrorMessage", "You must be logged in to do that action.");
         response.sendRedirect(baseUrl + "login.jsp");
      }
   }

   @Override
   public void init(FilterConfig arg0) throws ServletException
   {
      // TODO Auto-generated method stub

   }

}
