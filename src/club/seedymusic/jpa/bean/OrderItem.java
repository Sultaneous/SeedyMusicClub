package club.seedymusic.jpa.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
public class OrderItem
{
   @Id
   @GeneratedValue
   private int id;
   private int cdid;

   public OrderItem()
   {
   }

   public OrderItem(int cdid)
   {
      this.cdid = cdid;
   }

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
    * Gets the cd id.
    *
    * @return Returns a {@link int} containing the cdid.
    */
   public int getCdid()
   {
      return cdid;
   }

   /**
    * Sets the cd id.
    *
    * @param cdid
    *           The {@link int} containing the cdid to set.
    */
   public void setCdid(int cdid)
   {
      this.cdid = cdid;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (obj == null)
         return false;
      if (!this.getClass().equals(obj.getClass()))
         return false;

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

}
