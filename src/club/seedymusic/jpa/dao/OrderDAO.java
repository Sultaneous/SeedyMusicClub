package club.seedymusic.jpa.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import club.seedymusic.jpa.bean.Order;
import club.seedymusic.util.SessionManager;

/**
 * <h2>OrderDAO Class</h2>
 * <p>
 * This class performs the Order related business logic for orders. It relies on the Order Entity
 * Bean.
 * 
 * It has a one-to-many relationship to a table listing the CD ids for a given order. This relies on
 * the OrderItem Entity Bean and is part of a Set collection. Because the Hibernate mappings have
 * been configured to do an "Eager" fetch of this joined table, no specific code / method is
 * required to retrieve order items for an order other than simply retrieving the specific order
 * itself.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
public class OrderDAO
{
   private class ListParams
   {
      int id;
   }

   public enum ListActions
   {
      LIST_ALL, LIST_BY_ID
   };

   /**
    * Constructs a new OrderDAO.
    *
    */
   public OrderDAO()
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
    * This method will add an order to the database. The order information must be stored in the
    * Order Entity bean. Note that Order has a 1-to-Many relationship, as each order can contain
    * multiple OrderItems (basically, the cdid).
    * 
    * @param order
    *           - the Order entity bean, with associated OrderItem beans
    * @return boolean; true if successful, false otherwise
    */
   public boolean addOrder(Order order)
   {
      // Get session object
      Session session = createSession();

      // Declare a null transaction for rollover checks
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();
         session.save(order);
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
         // Clean-up
         session.close();
      }
   }

   /**
    * This will retrieve a list of ALL the Orders in the database. Once you have the list, you can
    * iterate over the contents; each will be an entity bean with the database record stored in its
    * attributes, accessible via the getter method accessors.
    * 
    * NOTE: You will need to do a nested iteration over the OrderItems set contained within.
    * 
    * @param action
    *           A value from the ListActions enumeration to state what action to take
    * @param listParams
    *           A ListParms object with parameter values or null if none
    * @return Returns a list of all the Orders in the database which can be iterated over.
    */
   private List<Order> listOrdersController(ListActions action, ListParams listParams)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         Criteria criteria = session.createCriteria(Order.class);

         if (action == ListActions.LIST_BY_ID) criteria.add(Restrictions.idEq(listParams.id));

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Order> orders = criteria.list();

         session.flush();
         transaction.commit();
         return (orders);
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

   public List<Order> listOrders()
   {
      // No params; just tell controller what action to take
      return (listOrdersController(ListActions.LIST_ALL, null));
   }

   public List<Order> listOrders(int id)
   {
      // Sanity check
      if (id < 1) return null;

      // Setup params object and tell controller what action to take
      ListParams listParams = new ListParams();
      listParams.id = id;
      return (listOrdersController(ListActions.LIST_BY_ID, listParams));
   }

   /**
    * Retrieves a single order object from the database based on its id.
    * 
    * @param id
    *           The order id to retrieve from database.
    * @return Returns the Order bean populated from db if id exists; null otherwise.
    */
   public Order getOrder(int id)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         Criteria criteria = session.createCriteria(Order.class);
         criteria.add(Restrictions.idEq(id));

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Order> orders = criteria.list();

         session.flush();
         transaction.commit();

         // Make sure we have a result
         if (orders == null || orders.isEmpty())
            return null;
         else
            return (orders.get(0));
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
         Criteria criteria = session.createCriteria(Order.class);
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
    * Allows an order's status to be updated, based on a valid order id. Sanity checks aren't
    * required on id and status as hibernate will throw an exception on failure.
    * 
    * @param id
    *           The id of the order to update.
    * @param status
    *           The new value for the status.
    * @return Returns true on success, false otherwise
    */
   public boolean setStatus(int id, String status)
   {
      // Create session
      Session session = createSession();
      Transaction transaction = null;

      try
      {
         // Transaction
         transaction = session.beginTransaction();

         // Using criteria requires no HQL or SQL or XML config data
         Criteria criteria = session.createCriteria(Order.class);
         criteria.add(Restrictions.idEq(id));

         // Suppress casting warning; this is a Hibernate issue
         @SuppressWarnings("unchecked")
         List<Order> orders = criteria.list();

         // Make sure we have a result; exception will be handle by catch block
         if (orders == null || orders.isEmpty()) throw (new HibernateException(
                  "The Order ID: " + id + " was not found in the database."));

         // Get order object
         Order order = orders.get(0);

         // Set the new status
         order.setStatus(status);

         // Update the database
         session.update(order);
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
