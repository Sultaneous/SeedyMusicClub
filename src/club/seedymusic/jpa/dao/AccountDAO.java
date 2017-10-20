package club.seedymusic.jpa.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import club.seedymusic.jpa.bean.Account;

public class AccountDAO
{
   public boolean addAccount(Account account)
   {
      try
      {
         // Configure Hibernate
         Configuration configuration = new Configuration().configure();

         // Create session factory
         StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                  .applySettings(configuration.getProperties());
         SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

         // Get session object
         Session session = sessionFactory.openSession();

         // Transaction
         Transaction transaction = session.beginTransaction();
         session.save(account);
         transaction.commit();

         return true;
      }
      catch (HibernateException e)
      {
         System.out.println("ERROR: " + e.getMessage());
         e.printStackTrace();
         return false;
      }
   }
}
