package club.seedymusic.controller;
import java.io.BufferedReader;
//import java.io.IOException;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
*/

//import org.glassfish.jersey.client.ClientConfig;
//import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.seedymusic.jpa.bean.Cd;

public class CatalogViewModel {
	
	private String _genre;
	private String _page;
	private String _search;
	private String _paginationStartPage;
	
	public CatalogViewModel()
	{
		
	}
	
	/*sets the genre for the model to be represented*/
	public void setGenre (String genre)
	{
		_genre= genre;
	}
	
	/*sets the page number for the model that will be represented*/
	public void setPage (String page)
	{
		_page=page;
	}
	
	
	public void setSearch(String search)
	{
		_search=search;
	}
	
	public void setStartPage(String startPage)
	{
		_paginationStartPage=startPage;
	}

	
	/**
	 * @return list of Cds to be handed to the JSP
	 * 
	 * @param baseUrl of the running web that is for instance http://localhost:8080/MyMusicStore*/
	public List<Cd> getCds(String baseUrl)
	{
		URL url;
		
		  try {
                if(_genre=="" || _genre==null)
				{  	
                  if(_page!="" && _page!=null)
                  {
                	  	if(_search!="" &&_search!=null)
                	  	{
                	  		url= new URL(baseUrl + "rest/catalog/all/"+_page+"?search="+_search);
                	  	}else
                	  	{
                	  		url= new URL(baseUrl + "rest/catalog/all/"+_page);
                	  	}
                  }
                  else
                  {
                	   if(_search!="" && _search!=null)
                	   {
                	    url = new URL(baseUrl+"rest/catalog/all?search="+_search);
                	   }
                	   else 
                	   {
                    	url = new URL(baseUrl+"rest/catalog/all");
                	   }
                  }
				}
                else
                {
                	  if(_page!="" && _page!=null)
                	  {
                		if(_search!="" && _search!=null)  
                	    url= new URL(baseUrl+"rest/catalog/"+_genre+"/"+_page+"?search="+_search);
                		else
                    	    url= new URL(baseUrl+"rest/catalog/"+_genre+"/"+_page);
	
                	  }
                	  else
                	  { 
                		if(_search!="" && _search!=null)  
                    url= new URL(baseUrl+"rest/catalog/"+_genre+"?search="+_search);
                		else
                        url= new URL(baseUrl+"rest/catalog/"+_genre);

                	  }

                }
                
                
                //call the rest webservice and get json results
               
                //bypassing ssl
                doTrustToCertificates();

                HttpsURLConnection con= (HttpsURLConnection)url.openConnection();
                BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
                
                String result="";
                
                String input="";
                
                while((input =reader.readLine())!=null)
                {
                	result+=input;
                }
                
                
                ObjectMapper objectMapper= new ObjectMapper();
                
                List<Cd> listCd= objectMapper.readValue(result, new TypeReference<List<Cd>>() {});
                
                return listCd;
                
			  } catch (Exception e) {

				e.printStackTrace();

			  }
		
		  return null;
		
		
	}
	
	
	
	
	/**
	 * @param baseUrl this is the base url upon which rest call come after ex: https://localhost:8443/MyMusicStore/rest/catalog/cd/1   baseUrl in this case is https://localhost:8443/MyMusicStore 
	 * 
	 * @return a list of strings referring to the corresponding pages URLs
	 * */
	public List<PaginationItem> getPaginationItems (String baseUrl)
	{
		URL url;
		String paginationBaseUrl="";
		
		Long count=(long) 0;
		//get the total count of albums 
		try {
			
            if(_genre=="" || _genre==null)
			{              
            		if(_search!="" && _search!=null)
            		{
            			url= new URL(baseUrl+"rest/catalog/all/count?"+"search="+_search);
            			paginationBaseUrl= baseUrl+"browse?"+"search="+_search +"&&";
            			
            		}else
            		{           
            			url= new URL(baseUrl+"rest/catalog/all/count");
            			paginationBaseUrl= baseUrl+"browse?";
            		}
			}
            else
            {
            	  if(_search!="" && _search!=null)
            	  {
            		  url= new URL(baseUrl+"rest/catalog/"+_genre+"/count?"+"search="+_search);
               	  paginationBaseUrl= baseUrl+"browse?"+"genre="+_genre+ "&&" +"search="+_search+"&&";  
            		  
            	  }else {
            	   url= new URL(baseUrl+"rest/catalog/"+_genre+"/count");
            	   paginationBaseUrl= baseUrl+"browse?"+"genre="+_genre+"&&";
            	  }
            }
            
            
            //call the rest webservice and get json results
           
            //bypassing ssl
            doTrustToCertificates();

            HttpsURLConnection con= (HttpsURLConnection)url.openConnection();
            BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            String result="";
            
            String input="";
            
            while((input =reader.readLine())!=null)
            {
            	result+=input;
            }
            
            
            ObjectMapper objectMapper= new ObjectMapper();
            
            //number of albums in the result
             count= objectMapper.readValue(result, new TypeReference<Long>() {});
            
            
		  } catch (Exception e) {

			e.printStackTrace();

		  }
		
		List<PaginationItem> paginationItems= new ArrayList();
		
		//number of items per page is 9 which is set in the web service
		
		double numberOfPages=Math.ceil(((double)count)/9.0);
		
		int page=0;
		
		int startPage=0;
		int endPage=0;
		
		//get requested page
		if(tryParseInt(_page))
			page=Integer.parseInt(_page);
		
		PaginationItem item; 
		
		if(numberOfPages<=5)
		{  // less than 5 total pages so show all
			startPage=1;
			endPage=(int)numberOfPages;
		}
		else { //more than 5 total pages so calculate start and end pages
			if(page<=3)
			{
			  startPage=1;
			  endPage=5;
			}else if(page+2>=numberOfPages)
			{
				startPage=(int)numberOfPages-4;
				endPage=(int)numberOfPages;
				
			}else {
			   startPage= page-2;
			   endPage=page+2;
			}
			
		}
		

		//get base URL for pagination
		
		for(int i=startPage;i<=endPage;i++)
		{
			item= new PaginationItem();
			
			item.pageNumber=Integer.toString(i);
			
			item.pageURL=paginationBaseUrl+ "page=" +Integer.toString(i);
			
			
			paginationItems.add(item);
		}
		
	  return paginationItems;
	
	}
	
	
	
	/**
	 * @param  baseUrl this is the base url upon which rest call come after ex: https://localhost:8443/MyMusicStore/rest/catalog/cd/1   baseUrl in this case is https://localhost:8443/MyMusicStore
	 * @param cdId the id of the cd
	 * @return a cd object
	 */
	public Cd getCd(String baseUrl, int cdID)
	{
		URL url;
		
		
		try {
			
			url= new URL(baseUrl + "rest/catalog/cd/"+ Integer.toString(cdID));
			
		 //bypassing ssl
        doTrustToCertificates();

        HttpsURLConnection con= (HttpsURLConnection)url.openConnection();
        BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String result="";
        
        String input="";
        
        while((input =reader.readLine())!=null)
        {
        	result+=input;
        }
        
        
        ObjectMapper objectMapper= new ObjectMapper();
        
        Cd cd= objectMapper.readValue(result, new TypeReference<Cd>() {});
        return cd;
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	
	
	
	public List<String> getGenres(String baseUrl)
	{
		URL url;
		
		
		
		try {
		
	    url = new URL(baseUrl+"rest/catalog/genres");
		
		//bypassing ssl
        doTrustToCertificates();

        HttpsURLConnection con= (HttpsURLConnection)url.openConnection();
        BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String result="";
        
        String input="";
        
        while((input =reader.readLine())!=null)
        {
        	result+=input;
        }
                
        ObjectMapper objectMapper= new ObjectMapper();
        
        List<String> genres= objectMapper.readValue(result, new TypeReference<List<String>>() {});
        
        
        //add all option to genre
        genres.add(0, "All");
        
        return genres;
        
		}
		catch( Exception e)
		{
			
		}
		
		
		return null;
	}
	
	/**
	 * Bypassing SSL certificate check
	 * 
	 * @throws Exception Exception is thrown if no certificate is available
	 */
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
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}

}



