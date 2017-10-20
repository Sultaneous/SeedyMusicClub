package club.seedymusic.jpa.dao;

import java.util.List;

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

   public List<Cd> listCds()
   {
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         transaction = session.beginTransaction();
         List<Cd> cds = session.createQuery("FROM Cd").list();
         transaction.commit();
         return (cds);
      }
      catch (HibernateException e)
      {
         if (transaction != null)
            transaction.rollback();
         e.printStackTrace();
         return null;
      }
      finally
      {
         // Close session to clean up
         session.close();
      }
   }

} // Class
