package club.seedymusic.jpa.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

   @Id
   @GeneratedValue
   private int id;

   private int accountId;

   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "orderid")
   private Set<OrderItem> orderItems;

   @Temporal(TemporalType.TIMESTAMP)
   private Date date;

   /*
    * Constructor
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
    * @return Returns a {@link int} containing the id.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Sets the id.
    *
    * @param id
    *           The {@link int} containing the id to set.
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * Gets the accountId.
    *
    * @return Returns a {@link int} containing the accountId.
    */
   public int getAccountId()
   {
      return accountId;
   }

   /**
    * Sets the accountId.
    *
    * @param accountId
    *           The {@link int} containing the accountId to set.
    */
   public void setAccountId(int accountId)
   {
      this.accountId = accountId;
   }

   /**
    * Gets the orderItems.
    *
    * @return Returns a {@link Set<OrderItem>} containing the orderItems. This is the cd id.
    */
   public Set<OrderItem> getOrderItems()
   {
      return orderItems;
   }

   /**
    * Sets the orderItems.
    *
    * @param orderItems
    *           The {@link Set<OrderItem>} containing the orderItems to set. This is the cd id.
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
}
