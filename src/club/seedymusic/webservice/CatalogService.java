package club.seedymusic.webservice;
import club.seedymusic.jpa.dao.*;
import club.seedymusic.jpa.bean.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

@Path("catalog")
public class CatalogService {

	public static Integer pageSize =9;
	
	/**
	 * @return Returns a list of Cds of size pageSize from the database based on page number
	 * @param page this is the page number*/
	@GET
	@Path("all{p:/?}{page: ([0-9])*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cd> getAllCDs(@PathParam("page") String page, @QueryParam("search") String search)
	{
		//search will be a query string parameter
		
		int pageNumber=0;
		
		boolean parseSafe= tryParseInt(page);
		if(parseSafe)
		{
			pageNumber =Integer.parseInt(page);
		}
		
		List<Cd> CDs=null;
		
		CdDAO cdDAO = new CdDAO();
		
		if( search==null || search.isEmpty())
		{
		 CDs= new ArrayList<Cd>(cdDAO.listCds(pageNumber,pageSize));
		}
		else
		{
		CDs= new ArrayList<Cd>(cdDAO.searchCds(search, pageNumber, pageSize));
		}
		
		return CDs;
	}
	
	
	
	
	/**
	 * @param genre the Cd/album Genre
	 * @param page optional parameter for to retrieve items of a certain page
	 * @param search this is the title to search for
	 * @return Returns a list of CDs by Genre 
	 * */
	@GET
	@Path("/{genre}{p:/?}{page: ([0-9])*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cd> getCDByCategory(@PathParam("genre") String genre, @PathParam("page") String page, @QueryParam("search") String search)
	{
		
int pageNumber=0;
		
		boolean parseSafe= tryParseInt(page);
		if(parseSafe)
		{
			pageNumber =Integer.parseInt(page);
		}
		
		
		CdDAO cdDAO = new CdDAO();
	
		List<Cd>  CDs=null;
		
		if(search=="" || search==null)
		{
		CDs= new ArrayList<Cd>(cdDAO.listCds(genre,pageNumber,pageSize));
		}
		else {
	     CDs= new ArrayList<Cd>(cdDAO.searchCds(search, genre, pageNumber, pageSize));
		}
		
		return CDs;
	}
	
	
	
	
	/**
	 * 
	 * @param search this is the title to search for 
	 * @return the all the number of Cds in the database
	 */
	@GET
	@Path("all/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Long getAllCDsCount(@QueryParam("search") String search)
	{
		Long count=(long) 0;
		
		CdDAO cdDAO = new CdDAO();
		if(search==null)
			count=cdDAO.getCount("","");
		else
		count=cdDAO.getCount(search,"");
		
		
	return count;	
	}
	
	
	/**
	 * 
	 * @param cdId this is the id of the cd to retrieve
	 * @return a cd of the requested id 
	 */
	@GET
	@Path("cd/{cdid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cd getCdbyId(@PathParam("cdid") int cdId)
	{
		CdDAO cdDAO= new CdDAO();
		return cdDAO.getCd(cdId);
	}
	
	
   /**
    * 
    *
    * @param genre this is the genre that is being queried
    * @param search this is the title that is being searched for
    * @return the count of all albums with a specific genre and title
    */
	@GET
	@Path("{genre}/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Long getAllCDsGenreCount(@PathParam("genre") String genre, @QueryParam("search") String search)
	{
		Long count=(long) 0;
		
		CdDAO cdDAO = new CdDAO();
		count=cdDAO.getCount(search,genre);
		
		
	return count;	
	}
	
	
	
	/**
	 * 
	 * @return all the available genre
	 */
	@GET
	@Path("genres")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getGenres()
	{
		CdDAO cdDAO= new CdDAO();
		
		
		List<String> genres =cdDAO.getGenres();
		
		return genres;
				
	}
	
	/**
	 * 
	 * @param value value to be tested for parsing 
	 * @return true if the value was parsed without failure
	 */
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
}


