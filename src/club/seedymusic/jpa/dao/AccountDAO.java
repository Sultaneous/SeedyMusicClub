package club.seedymusic.jpa.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import club.seedymusic.jpa.bean.Account;

public class AccountDAO
{

   /**
    * Constructs a new AccountDAO. Argument free.
    *
    */
   public AccountDAO()
   {
      // Nothing to do (yet?)
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
         // Configure Hibernate
         Configuration configuration = new Configuration().configure();

         // Create session factory
         StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                  .applySettings(configuration.getProperties());
         SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
         return (sessionFactory.openSession());
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
         transaction.commit();

         // Success
         return true;
      }
      catch (HibernateException e)
      {
         // Rollback if necessary
         if (transaction != null)
            transaction.rollback();

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

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Account> accounts = criteria.list();

         transaction.commit();
         return (accounts);
      }
      catch (HibernateException e)
      {
         // Check if rollback is required
         if (transaction != null)
            transaction.rollback();
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

}
