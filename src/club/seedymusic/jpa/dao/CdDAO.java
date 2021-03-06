package club.seedymusic.jpa.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import club.seedymusic.jpa.bean.Cd;
import club.seedymusic.util.ConfigurationManager;
import club.seedymusic.util.SessionManager;

/**
 * <h2>CdDAO Class</h2>
 * <p>
 * This class performs the Data related business logic for CDs. It relies on the CD Entity Bean.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
public class CdDAO
{
   /**
    * The name of the configuration file.
    */
   private static final String CONFIG_FILE = "dao.configuration.properties";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_ID = "cds_id";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_GENRE = "cds_genre";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_TITLE = "cds_title";

   /**
    * Constant representing a default field name in case the configuration file failed to load.
    */
   private static final String DEFAULT_FIELD_ID = "id";

   /**
    * Constant representing a default field name in case the configuration file failed to load.
    */
   private static final String DEFAULT_FIELD_GENRE = "genre";

   /**
    * Constant representing a default field name in case the configuration file failed to load.
    */
   private static final String DEFAULT_FIELD_TITLE = "title";

   /**
    * <h2>ListControllerParameters Class</h2>
    * <p>
    * This is a private nested class that provides parameters for the list controller and list
    * methods, allowing greater code refactoring.
    * 
    * @author Karim Sultan
    * 
    * @version Oct 25, 2017 Created this class.
    */
   private class ListControllerParameters
   {

      // Action constants
      /**
       * ACTION: list all the CDs in database in entirety.
       */
      private static final int ACTION_LIST = 1;

      /**
       * ACTION: list pages of CDs from DB.
       */
      private static final int ACTION_LIST_PAGED = 2;

      /**
       * ACTION: list genre matching CDs from DB.
       */
      private static final int ACTION_LIST_BYGENRE = 3;

      /**
       * ACTION: list pages of genre matching CDs from DB.
       */
      private static final int ACTION_LIST_BYGENRE_PAGED = 4;

      /**
       * ACTION: searches DB for matches and near matches of title criteria.
       */
      private static final int ACTION_SEARCH = 5;

      /**
       * ACTION: searches DB for title matches and returns as page.
       */
      private static final int ACTION_SEARCH_PAGED = 6;

      /**
       * ACTION: searches DB for title & genre matches.
       */
      private static final int ACTION_SEARCHFULL = 7;

      /**
       * ACTION: searches DB for title & genre matches and returns as page.
       */
      private static final int ACTION_SEARCHFULL_PAGED = 6;

      /**
       * The action to perform; must be one of the class ACTION constants.
       */
      public int action;

      /**
       * The genre criteria.
       */
      public String genre;

      /**
       * The title criteria.
       */
      public String title;

      /**
       * The page start boundary.
       */
      public int pageStart;

      /**
       * The maximum number of records per page.
       */
      public int pageSize;
   }

   /**
    * Holds the mappings for string literals so that no fields are stored in the class, except some
    * default values (overridden by the configuration). This abstracts all HQl / SQL field name
    * references from the code and avoids recompilations on change.
    */
   private ConfigurationManager configurationManager;

   /**
    * Constructs a new CdDAO.
    *
    */
   public CdDAO()
   {
      // Load the configuration. The configuration keeps a dictionary of fields for
      // the actual DB, abstracted from us, so if they change, one needs only update
      // the properties file and not the code.
      configurationManager = new ConfigurationManager(CONFIG_FILE);
   }

   /**
    * This method is a utility method for session creation, refactored from repetitive code.
    * 
    * @return A Hibernate Session object to be used by transactions and queries.
    */
   private Session createSession()
   {
      try
      {
         // Get a session from our singleton session factory
         return (SessionManager.getSessionFactory().openSession());
      }
      catch (HibernateException e)
      {
         e.printStackTrace();
         return (null);
      }
   }

   /**
    * This method will add a cd to the database. The cd information must be stored in the Cd Entity
    * bean.
    * 
    * @param cd
    *           The Cd Bean with populated fields to add to the database.
    * @return boolean; true if successful, false otherwise.
    */
   public boolean addCd(Cd cd)
   {
      // Sanity test
      if (cd == null) return false;

      // Get session object
      Session session = createSession();

      // Declare a null transaction for rollover checks
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();
         session.save(cd);
         session.flush();
         transaction.commit();

         // Success
         return true;
      }
      catch (HibernateException e)
      {
         // Rollback if necessary
         if (transaction != null) transaction.rollback();

         e.printStackTrace();

         // Failure
         return false;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

   /**
    * This method follows a controller design pattern and provides the workhorse logic for
    * retrieving lists of CDs from the database based on criteria. The public API functions are
    * shells which call this with an action and the required parameters (stashed in a
    * {@link ListControllerParameters} object). The controller will then set the criteria as
    * required and return a list of matching Cd beans.
    * 
    * @param lcp
    *           A {@link ListControllerParameters} object containing criteria
    * @return List<Cd> A list of (@link club.seedymusic.jpa.bean.Cd} objects from the DB
    */
   private List<Cd> listCdsController(ListControllerParameters lcp)
   {
      // Sanity check
      if (lcp == null) return null;

      // Create session
      Session session = createSession();
      Transaction transaction = null;

      if (lcp.title != null) System.out.println("[SEARCH] " + lcp.title);

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         // Hibernate 5 deprecated this API in favour of JPA, which is 20x more verbose
         @SuppressWarnings("deprecation")
         Criteria criteria = session.createCriteria(Cd.class);

         // The request is to list all CDs
         if (lcp.action == ListControllerParameters.ACTION_LIST)
         {
            // Return all ordered by id
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
         }

         // The request is to list all CDs but paged
         else if (lcp.action == ListControllerParameters.ACTION_LIST_PAGED)
         {
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
            criteria.setFirstResult((lcp.pageStart - 1) * lcp.pageSize);
            criteria.setMaxResults(lcp.pageSize);
         }

         // The request is to list CDs by their genre
         else if (lcp.action == ListControllerParameters.ACTION_LIST_BYGENRE)
         {
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
            // was using .eq but got unpredictable results, .ilike works and is case insensitive.
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_GENRE, DEFAULT_FIELD_GENRE),
                     lcp.genre, MatchMode.EXACT));
         }

         // The request is to list CDs by genre but paged
         else if (lcp.action == ListControllerParameters.ACTION_LIST_BYGENRE_PAGED)
         {
            // criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_GENRE, DEFAULT_FIELD_GENRE),
                     lcp.genre, MatchMode.EXACT));
            criteria.setFirstResult((lcp.pageStart - 1) * lcp.pageSize);
            criteria.setMaxResults(lcp.pageSize);
         }

         // Its a search request based on title
         else if (lcp.action == ListControllerParameters.ACTION_SEARCH)
         {
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_TITLE, DEFAULT_FIELD_TITLE),
                     lcp.title, MatchMode.ANYWHERE));
         }

         // The request is a title search, paged.
         else if (lcp.action == ListControllerParameters.ACTION_SEARCH_PAGED)
         {
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_TITLE, DEFAULT_FIELD_TITLE),
                     lcp.title, MatchMode.ANYWHERE));
            criteria.setFirstResult((lcp.pageStart - 1) * lcp.pageSize);
            criteria.setMaxResults(lcp.pageSize);
         }

         // Its a search request based on title
         else if (lcp.action == ListControllerParameters.ACTION_SEARCHFULL)
         {
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_TITLE, DEFAULT_FIELD_TITLE),
                     lcp.title, MatchMode.ANYWHERE));
            criteria.add(Restrictions.eq(
                     configurationManager.getConfiguration(CONFIG_GENRE, DEFAULT_FIELD_GENRE),
                     lcp.genre));
         }

         // The request is a title search, paged.
         else if (lcp.action == ListControllerParameters.ACTION_SEARCHFULL_PAGED)
         {
            criteria.addOrder(
                     Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_TITLE, DEFAULT_FIELD_TITLE),
                     lcp.title, MatchMode.ANYWHERE));
            criteria.add(Restrictions.eq(
                     configurationManager.getConfiguration(CONFIG_GENRE, DEFAULT_FIELD_GENRE),
                     lcp.genre));
            criteria.setFirstResult((lcp.pageStart - 1) * lcp.pageSize);
            criteria.setMaxResults(lcp.pageSize);
         }

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Cd> cds = criteria.list();

         session.flush();
         transaction.commit();
         return (cds);
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
         if (transaction != null) transaction.rollback();

         e.printStackTrace();

         // Failure
         return null;
      }
      catch (Exception e)
      {
         // Probably coding error and LCP isn't set properly
         e.printStackTrace();

         // Failure
         return null;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

   /**
    * This will retrieve a list of ALL the cds in the database. Once you have the list, you can
    * iterate over the contents; each will be an entity bean with the database record stored in its
    * attributes, accessible via the getter method accessors.
    * 
    * @return Returns a list of all the CDs in the database which can be iterated over; or null
    *         otherwise.
    */
   public List<Cd> listCds()
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_LIST;
      return (listCdsController(lcp));
   }

   /**
    * Lists Cds by paging within the bounds of pageStart, pageStart + pageSize - 1
    * 
    * @param pageStart
    *           The record to start at (inclusive, 0-based)
    * @param pageSize
    *           The maximum number of records to return
    * @return A list of Cd beans with a count within the page size or null on failure.
    */
   public List<Cd> listCds(int pageStart, int pageSize)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_LIST_PAGED;
      lcp.pageStart = pageStart;
      lcp.pageSize = pageSize;
      return (listCdsController(lcp));
   }

   /**
    * Returns a list of Cd beans that match a certain genre. Case-sensitive; genres are stored in
    * lower case in the DB.
    * 
    * @param genre
    *           The genre (ie, "rock") that is being searched for; use lower-case.
    * @return List<Cd> a list of matching Cd beans or null otherwise
    */
   public List<Cd> listCds(String genre)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_LIST_BYGENRE;
      lcp.genre = genre;
      return (listCdsController(lcp));
   }

   /**
    * Lists a page of Cds matching a genre within the bounds of pageStart, pageStart + pageSize - 1
    * 
    * @param genre
    *           The genre to search for (ie, "blues"). Use lower-case.
    * @param pageStart
    *           The record to start at (inclusive, 0-based)
    * @param pageSize
    *           The maximum number of records to return
    * @return A list of matching Cd beans with a count within the page size or null on failure.
    */
   public List<Cd> listCds(String genre, int pageStart, int pageSize)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_LIST_BYGENRE_PAGED;
      lcp.genre = genre;
      lcp.pageStart = pageStart;
      lcp.pageSize = pageSize;
      return (listCdsController(lcp));
   }

   /**
    * Searches for any CDs that match, in part, the title search criteria.
    * 
    * @param title
    *           The search criteria (ie, "Love" would find all CDs with "Love" in their title).
    * @return A list of matching Cd beans or null otherwise.
    */
   public List<Cd> searchCds(String title)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_SEARCH;
      lcp.title = title;
      return (listCdsController(lcp));
   }

   /**
    * Searches for any CDs that match, in part, the title search criteria and returns a page of
    * results bounded by pageStart, pageStart + pageSize - 1.
    * 
    * @param title
    *           The search criteria (ie, "Love" would find all CDs with "Love" in their title).
    * @param pageStart
    *           The record to start at (inclusive, 0-based)
    * @param pageSize
    *           The maximum number of records to return
    * @return A list of matching Cd beans or null otherwise.
    */
   public List<Cd> searchCds(String title, int pageStart, int pageSize)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_SEARCH_PAGED;
      lcp.title = title;
      lcp.pageStart = pageStart;
      lcp.pageSize = pageSize;
      return (listCdsController(lcp));
   }

   /**
    * Searches for any CDs that match, in part, the title and genre search criteria.
    * 
    * @param title
    *           The search criteria (ie, "Love" would find all CDs with "Love" in their title).
    *           Matches partially.
    * @param genre
    *           The search criteria (ie, "blues" would find all CDs with "blues" as their genre).
    *           Matches exactly.
    * @return A list of matching Cd beans or null otherwise.
    */
   public List<Cd> searchCds(String title, String genre)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_SEARCHFULL;
      lcp.title = title;
      lcp.genre = genre;
      return (listCdsController(lcp));
   }

   /**
    * Searches for any CDs that match, in part, the title search criteria and returns a page of
    * results bounded by pageStart, pageStart + pageSize - 1.
    * 
    * @param title
    *           The search criteria (ie, "Love" would find all CDs with "Love" in their title).
    *           Matches partially.
    * @param genre
    *           The search criteria (ie, "blues" would find all CDs with "blues" as their genre).
    *           Matches exactly.
    * @param pageStart
    *           The record to start at (inclusive, 0-based)
    * @param pageSize
    *           The maximum number of records to return
    * @return A list of matching Cd beans or null otherwise.
    */
   public List<Cd> searchCds(String title, String genre, int pageStart, int pageSize)
   {
      ListControllerParameters lcp = new ListControllerParameters();
      lcp.action = ListControllerParameters.ACTION_SEARCHFULL_PAGED;
      lcp.title = title;
      lcp.genre = genre;
      lcp.pageStart = pageStart;
      lcp.pageSize = pageSize;
      return (listCdsController(lcp));
   }

   /**
    * Retrieves a single Cd object from the database based on its id.
    * 
    * @param id
    *           The CD id to retrieve from database.
    * @return Returns the Cd bean populated from db if id exists; null otherwise.
    */
   public Cd getCd(int id)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         // Hibernate 5 deprecated this API in favour of JPA, which is 20x more verbose
         @SuppressWarnings("deprecation")
         Criteria criteria = session.createCriteria(Cd.class);
         criteria.add(Restrictions.idEq(id));

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Cd> cds = criteria.list();

         session.flush();
         transaction.commit();

         // Make sure we have a result
         if (cds == null || cds.isEmpty())
            return null;
         else
            return (cds.get(0));
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
         if (transaction != null) transaction.rollback();

         e.printStackTrace();

         // Failure
         return null;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

   /**
    * Returns the number of records in the table.
    * 
    * @return long Number of records
    */
   public long getCount()
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         // Hibernate 5 deprecated this API in favour of JPA, which is 20x more verbose
         @SuppressWarnings("deprecation")
         Criteria criteria = session.createCriteria(Cd.class);
         long records = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult())
                  .longValue();

         session.flush();
         transaction.commit();

         // Make sure we have a result
         return (records);
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
         if (transaction != null) transaction.rollback();

         e.printStackTrace();

         // Failure
         return 0;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

   /**
    * Gets the number of CD records that near-match the title and exact match the genre criteria.
    * 
    * @param title
    *           The title to near match
    * @param genre
    *           The genre to exact match
    * @return Returns the count of matching records, or 0 on failure
    */
   public long getCount(String title, String genre)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         // Hibernate 5 deprecated this API in favour of JPA, which is 20x more verbose
         @SuppressWarnings("deprecation")
         Criteria criteria = session.createCriteria(Cd.class);
         if (title != null && !title.isEmpty())
         {
            criteria.add(Restrictions.ilike(
                     configurationManager.getConfiguration(CONFIG_TITLE, DEFAULT_FIELD_TITLE),
                     title, MatchMode.ANYWHERE));
         }

         if (genre != null && !genre.isEmpty())
         {
            criteria.add(Restrictions.eq(
                     configurationManager.getConfiguration(CONFIG_GENRE, DEFAULT_FIELD_GENRE),
                     genre));
         }

         // Get the count
         long records = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult())
                  .longValue();

         session.flush();
         transaction.commit();

         // Make sure we have a result
         return (records);
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
         if (transaction != null) transaction.rollback();

         e.printStackTrace();

         // Failure
         return 0;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

   /**
    * Creates a list of genres (product categories).
    * 
    * @return A List<string> of all genres in the cds table.
    */
   public List<String> getGenres()
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         // Hibernate 5 deprecated this API in favour of JPA, which is 20x more verbose
         @SuppressWarnings("deprecation")
         Criteria criteria = session.createCriteria(Cd.class);
         criteria.setProjection(Projections.distinct(Projections.property(
                  configurationManager.getConfiguration(CONFIG_GENRE, DEFAULT_FIELD_GENRE))))
                  .addOrder(Order.asc(configurationManager.getConfiguration(CONFIG_GENRE,
                           DEFAULT_FIELD_GENRE)));

         // Annotation necessary to handle Hibernate type-safety bug
         @SuppressWarnings("unchecked")
         List<String> genres = criteria.list();

         session.flush();
         transaction.commit();

         // Caller should check for empty set and null values
         return (genres);
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
         if (transaction != null) transaction.rollback();

         e.printStackTrace();

         // Failure
         return null;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

} // Class
