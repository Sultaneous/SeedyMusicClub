package club.seedymusic.util;

import java.util.Properties;

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

         // NOTE: This addition ends my nightmare of upgrading from Hibernate 4 to 5.
         // Nowhere did they explain in their docs that the config file XML mapping
         // <mapping class="..."/> had been REMOVED. FINALLY figured out the mapping
         // was the root problem and doing it programmatically was the only solution.
         // Manually add our mapping classes
         configuration.addAnnotatedClass(club.seedymusic.jpa.bean.Cd.class);
         configuration.addAnnotatedClass(club.seedymusic.jpa.bean.Account.class);
         configuration.addAnnotatedClass(club.seedymusic.jpa.bean.Order.class);
         configuration.addAnnotatedClass(club.seedymusic.jpa.bean.OrderItem.class);

         Properties properties = new Properties();
         properties.put("hibernate.id.new_generator_mappings", "false");
         configuration.addProperties(properties);

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
