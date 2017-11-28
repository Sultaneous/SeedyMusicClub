package club.seedymusic.jpa.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <h2>Order Class</h2>
 * <p>
 * This class is the entity bean used by the Java Persistence API. It represents an Order entry in
 * the database.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
@Entity
@Table(name = "orders")
public class Order
{
   /*
    * Fields
    */

   /**
    * The unique id for the row entry in the table.
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   /**
    * Represents the id of the account making the order.
    */
   private int accountId;

   /**
    * Open field to represent the status. Not enumerated; can be set to anything by the controller
    * or web-service as they require depending on how the order system is envisioned (for example,
    * "open", "paid", "pending", "shipped").
    */
   private String status;

   /**
    * A one-to-many joined table containing all the ids of the cds that are being ordered.
    */
   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "orderid")
   @OrderBy("id")
   private Set<OrderItem> orderItems;

   /**
    * Timestamp at time of record creation / update.
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date date;

   /*
    * Constructor
    */

   /**
    * Constructs a new Order.
    *
    */
   public Order()
   {
      this.accountId = 0;
      this.orderItems = new HashSet<OrderItem>();
      this.date = new Date();
   }

   /*
    * Accessors (Getters and Setters)
    */

   /**
    * Gets the id.
    *
    * @return Returns an int containing the id.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Sets the id.
    *
    * @param id
    *           The int containing the id to set.
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * Gets the accountId.
    *
    * @return Returns an int containing the accountId.
    */
   public int getAccountId()
   {
      return accountId;
   }

   /**
    * Sets the accountId.
    *
    * @param accountId
    *           The int containing the accountId to set.
    */
   public void setAccountId(int accountId)
   {
      this.accountId = accountId;
   }

   /**
    * Gets the status.
    *
    * @return Returns a {@link String} containing the status.
    */
   public String getStatus()
   {
      return status;
   }

   /**
    * Sets the status.
    *
    * @param status
    *           The {@link String} containing the status to set.
    */
   public void setStatus(String status)
   {
      this.status = status;
   }

   /**
    * Gets the orderItems.
    *
    * @return Returns a {@link Set} containing the orderItems. This is the CD id.
    */
   public Set<OrderItem> getOrderItems()
   {
      return orderItems;
   }

   /**
    * Sets the orderItems.
    *
    * @param orderItems
    *           The {@link Set} containing the orderItems to set. This is the CD id.
    */
   public void setOrderItems(Set<OrderItem> orderItems)
   {
      this.orderItems = orderItems;
   }

   /**
    * Gets the date.
    *
    * @return Returns a {@link Date} containing the date.
    */
   public Date getDate()
   {
      return date;
   }

   /**
    * Sets the date.
    *
    * @param date
    *           The {@link Date} containing the date to set.
    */
   public void setDate(Date date)
   {
      this.date = date;
   }

   // Methods

   /**
    * Converts the object to a pretty formatted JSON string.
    * 
    * @return String value containing the JSON representation.
    */
   public String toJson()
   {
      // Convert object to pretty JSON string
      ObjectMapper objectMapper = new ObjectMapper();

      String json;

      try
      {
         json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
         return (json);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return (null);
      }
   }

   /**
    * Deserializes a JSON string into an Order object.
    * 
    * @param Json
    *           - the JSON string to inflate.
    * @return An Order object on success or null on failure.
    */
   public static Order createObject(String Json)
   {
      ObjectMapper objectMapper = new ObjectMapper();
      try
      {
         Order order = objectMapper.readValue(Json, Order.class);
         return (order);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return (null);
      }
   }

} // Class
