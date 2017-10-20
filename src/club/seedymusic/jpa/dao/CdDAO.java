package club.seedymusic.jpa.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import club.seedymusic.jpa.bean.Cd;

public class CdDAO
{
   public CdDAO()
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
    * This method will add a cd to the database. The cd information must be stored in the Cd Entity
    * bean.
    * 
    * @param cd
    * @return boolean; true if successful, false otherwise
    */
   public boolean addCd(Cd cd)
   {
      // Get session object
      Session session = createSession();

      // Declare a null transaction for rollover checks
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();
         session.save(cd);
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
         // Close session to clean up
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
   public List<Cd> listCds()
   {
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         Criteria criteria = session.createCriteria(Cd.class);

         // Alternative way to load table data using HQL
         // List<Cd> cds = session.createQuery("FROM Cd").list();

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Cd> cds = criteria.list();

         transaction.commit();
         return (cds);
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

} // Class
