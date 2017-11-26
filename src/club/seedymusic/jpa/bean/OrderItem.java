package club.seedymusic.jpa.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <h2>OrderItem Class</h2>
 * <p>
 * This class is the entity bean used by the Java Persistence API. It represents an ordered item
 * entry in the database.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
@Entity
@Table(name = "orderitems")
public class OrderItem
{
   /**
    * The unique ID for the record in the table.
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   /**
    * The id of the CD that was ordered.
    */
   private int cdid;

   /**
    * Constructs a new OrderItem.
    *
    */
   public OrderItem()
   {
      // Nothing to do (yet?)
   }

   /**
    * Constructs a new OrderItem.
    *
    * @param cdid
    *           The id of the CD which has been ordered.
    */
   public OrderItem(int cdid)
   {
      this.cdid = cdid;
   }

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
    * Gets the cd id.
    *
    * @return Returns an int containing the cdid.
    */
   public int getCdid()
   {
      return cdid;
   }

   /**
    * Sets the cd id.
    *
    * @param cdid
    *           The int containing the cdid to set.
    */
   public void setCdid(int cdid)
   {
      this.cdid = cdid;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (obj == null) return false;
      if (!this.getClass().equals(obj.getClass())) return false;

      OrderItem obj2 = (OrderItem) obj;
      if ((this.id == obj2.getId()) && (this.cdid == obj2.getCdid()))
      {
         return true;
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      int hash = 0;
      String digest = Integer.toString(id) + Integer.toString(cdid);
      hash = digest.hashCode();
      return hash;
   }

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
    * Deserializes a JSON string into an OrderItem object.
    * 
    * @param Json
    *           - the JSON string to inflate.
    * @return An OrderItem object on success or null on failure.
    */
   public static OrderItem createObject(String Json)
   {
      ObjectMapper objectMapper = new ObjectMapper();
      try
      {
         OrderItem orderItem = objectMapper.readValue(Json, OrderItem.class);
         return (orderItem);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return (null);
      }
   }

} // Class
