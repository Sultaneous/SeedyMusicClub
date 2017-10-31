package club.seedymusic.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import club.seedymusic.jpa.bean.Cd;

/**
 * Servlet implementation class Catalog
 */
@WebServlet("/browse")
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//ex: seedymusic.club/browse?genre=rock get cds with specific genre
		//ex :seedymusic.club/browse get all cds regardless of genre
		

		//Get Genre from query string 
		String genre= request.getParameter("genre");
		if(genre!=null && genre.equals("All"))
		{
			genre=null;
		}
		
		
		//Get page number from query string 
		String page= request.getParameter("page");
		
		//Get search term from query string
		String search= request.getParameter("search");
		
		//initialize a view model
		CatalogViewModel model= new CatalogViewModel();
		//calls a modifier to set the genre for the model
		model.setGenre(genre);
		//calls a modifier to set the page for the model
		model.setPage(page);
		// calls a modifier to set the search term for the model
		model.setSearch(search);
		
		String url = request.getRequestURL().toString();
		String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
		
		List<PaginationItem> paginationItems= model.getPaginationItems(baseURL);
		
		
		//retrieve all cds of the corresponding genre if no genre was specified then get all cds.
		List<Cd> listCd= model.getCds(baseURL);
		
		//get list of all genres
		List<String> genres= model.getGenres(baseURL);
		
		//session
		HttpSession session = request.getSession(true);

		
		//save data to be retrieved at jsp page
		request.setAttribute("listCd", listCd);
		request.setAttribute("paginationItems", paginationItems);
	
		//save in session 
		session.setAttribute("genres", genres);
		
		if(page==null)
			page="1";
		session.setAttribute("currentPage", page);
		
		session.setAttribute("currentGenre", genre);
		session.setAttribute("currentSearch", search);
		
		RequestDispatcher dispatcher;
		
		dispatcher= request.getRequestDispatcher("catalog.jsp");
		dispatcher.forward(request, response);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
