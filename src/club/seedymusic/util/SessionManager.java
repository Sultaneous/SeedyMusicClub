package club.seedymusic.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionManager
{
   private static final SessionFactory sessionFactory = initSessionFactory();

   private static SessionFactory initSessionFactory()
   {
      try
      {
         // Configure Hibernate
         Configuration configuration = new Configuration().configure();

         // Create session factory
         StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                  .applySettings(configuration.getProperties());
         SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

         // This will be our singleton
         return (sessionFactory);
      }
      catch (HibernateException e)
      {
         e.printStackTrace();
         return (null);
      }
   }

   public static SessionFactory getSessionFactory()
   {
      return sessionFactory;
   }



}
