package club.seedymusic.jpa.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import club.seedymusic.jpa.bean.Account;
import club.seedymusic.util.ConfigurationManager;
import club.seedymusic.util.SessionManager;

/**
 * <h2>AccountDAO Class</h2>
 * <p>
 * This class performs the Data related business logic for Accounts. It relies on the Account Entity
 * Bean.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
public class AccountDAO
{
   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_USERNAME = "accounts_username";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String DEFAULT_FIELD_USERNAME = "username";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_ID = "accounts_id";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String DEFAULT_FIELD_ID = "id";

   /**
    * Holds the mappings for string literals so that no fields are stored in the class, except some
    * default values (overridden by the configuration). This abstracts all HQl / SQL field name
    * references from the code and avoids recompilations on change.
    */
   private ConfigurationManager configurationManager;

   /**
    * Constructs a new AccountDAO. Argument free.
    *
    */
   public AccountDAO()
   {
      // Load the configuration. The configuration keeps a dictionary of fields for
      // the actual DB, abstracted from us, so if they change, one needs only update
      // the properties file and not the code.
      configurationManager = new ConfigurationManager();
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
    * Adds an account to the account table.
    * 
    * @param account
    *           The Account bean, with populated fields.
    * @return A boolean value (true if successful, failure otherwise).
    */
   public boolean addAccount(Account account)
   {
      // Get session object
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();
         session.save(account);

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
         // Clean-up resources
         session.close();
      }
   }

   /**
    * This will retrieve a list of ALL the cds in the database. Once you have the list, you can
    * iterate over the contents; each will be an entity bean with the database record stored in its
    * attributes, accessible via the getter method accessors.
    * 
    * @return Returns a list of all the CDs in the database which can be iterated over.
    */
   public List<Account> listAccounts()
   {
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria allows us to avoid HQL / SQL string literals
         Criteria criteria = session.createCriteria(Account.class);

         // Return all ordered by id
         criteria.addOrder(
                  Order.asc(configurationManager.getConfiguration(CONFIG_ID, DEFAULT_FIELD_ID)));

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Account> accounts = criteria.list();

         session.flush();
         transaction.commit();
         return (accounts);
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
    * Retrieves a single account object from the database based on its id.
    * 
    * @param id
    *           The account id to retrieve from database.
    * @return Returns the Account bean populated from db if id exists; null otherwise.
    */
   public Account getAccount(int id)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         Criteria criteria = session.createCriteria(Account.class);
         criteria.add(Restrictions.idEq(id));

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Account> accounts = criteria.list();

         session.flush();
         transaction.commit();

         // Make sure we have a result
         if (accounts.isEmpty())
            return null;
         else
            return (accounts.get(0));
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
    * Searches the database for an account by username. This should be a unique record but
    * regarless, if multiple ones are found, we return only the first one (defined b by the one with
    * the lowest id).
    * 
    * @param username
    *           The unique username to search for
    * @return Returns the account object or null on failure.
    */
   public Account getAccount(String username)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         Criteria criteria = session.createCriteria(Account.class);

         // Search for username. It should be unique. Regardless, we will
         // return only the first result.
         criteria.add(Restrictions.eq(
                  configurationManager.getConfiguration(CONFIG_USERNAME, DEFAULT_FIELD_USERNAME),
                  username));
         criteria.uniqueResult();

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Account> accounts = criteria.list();

         session.flush();
         transaction.commit();

         // Make sure we have a result
         if (accounts.isEmpty())
            return null;
         else
            return (accounts.get(0));
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
         Criteria criteria = session.createCriteria(Account.class);
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
    * Deletes a single account object from the database based on its id.
    * 
    * NOTE: Do NOT use this call in a web-accessible tier! This call is for internal administration
    * and tools only.
    * 
    * @param id
    *           The account id to delete from database.
    * @return True if successful, false otherwise.
    */
   public boolean deleteAccount(int id)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         Account account = new Account();
         account.setId(id);

         session.delete(account);

         session.flush();
         transaction.commit();

         // Success
         return (true);
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
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


} // Class
