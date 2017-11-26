package club.seedymusic.jpa.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <h2>Cd Class</h2>
 * <p>
 * This class is the entity bean used by the Java Persistence API. It represents a CD entry in the
 * database.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
@Entity
@Table(name = "cds")
public class Cd
{
   /*
    * Constants
    */

   /**
    * Default field value for string fields. Marked transient so Hibernate doesn't put it into the
    * database table as a column.
    */
   @Transient
   private final String DEFAULT_VALUE = "n/a";

   /**
    * This is the unique key for the object in the database
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   /*
    * Fields
    */

   /**
    * The title of the CD (ie, "The Moon is Round")
    */
   private String title;

   /**
    * The name of the band / singer who made the CD.
    */
   private String band;

   /**
    * The genre of the CD (ie, "Rock") NOTE: This is NOT an enumeration, in order to allow more
    * genre types to be easily added
    */
   private String genre;

   /**
    * The price of the CD (ie, 9.99)
    */
   private double price;

   /**
    * The link to the cover art (ie, "/assets/cds/covers/cover001.jpg")
    */
   private String cover;

   /**
    * The link to the audio sample (ie, "/assets/cds/samples/smaple001.mp3")
    */
   private String sample;

   /**
    * The quantity of items in stock.
    */
   private int quantity;

   /**
    * Internal; provides date and time stamp of when the record was created or modified.
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date date;

   /**
    * Constructs a new Cd. Hibernate requires an argument free constructor. This will set default
    * values into the bean.
    */
   public Cd()
   {
      this.title = DEFAULT_VALUE;
      this.genre = DEFAULT_VALUE;
      this.cover = DEFAULT_VALUE;
      this.sample = DEFAULT_VALUE;
      this.quantity = 0;
      this.price = 0f;

      // CHANGED: No longer need to set date; as the
      // DB will set this on creation and on updates
      // this.date = new Date();
   }

   /*
    * Accessors: Getters and Setters
    */

   /**
    * Gets the date the record was last updated or created.
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
    * Gets the title.
    *
    * @return Returns a {@link String} containing the title.
    */
   public String getTitle()
   {
      return title;
   }

   /**
    * Sets the title.
    *
    * @param title
    *           The {@link String} containing the title to set.
    */
   public void setTitle(String title)
   {
      this.title = title;
   }

   /**
    * Gets the band.
    *
    * @return Returns a {@link String} containing the band.
    */
   public String getBand()
   {
      return band;
   }

   /**
    * Sets the band.
    *
    * @param band
    *           The {@link String} containing the band to set.
    */
   public void setBand(String band)
   {
      this.band = band;
   }

   /**
    * Gets the genre.
    *
    * @return Returns a {@link String} containing the genre.
    */
   public String getGenre()
   {
      return genre;
   }

   /**
    * Sets the genre.
    *
    * @param genre
    *           The {@link String} containing the genre to set.
    */
   public void setGenre(String genre)
   {
      this.genre = genre;
   }

   /**
    * Gets the price.
    *
    * @return Returns a float containing the price.
    */
   public double getPrice()
   {
      return price;
   }

   /**
    * Sets the price.
    *
    * @param price
    *           The float containing the price to set.
    */
   public void setPrice(double price)
   {
      this.price = price;
   }

   /**
    * Gets the cover.
    *
    * @return Returns a {@link String} containing the cover.
    */
   public String getCover()
   {
      return cover;
   }

   /**
    * Sets the cover.
    *
    * @param cover
    *           The {@link String} containing the cover to set.
    */
   public void setCover(String cover)
   {
      this.cover = cover;
   }

   /**
    * Gets the sample.
    *
    * @return Returns a {@link String} containing the sample.
    */
   public String getSample()
   {
      return sample;
   }

   /**
    * Sets the sample.
    *
    * @param sample
    *           The {@link String} containing the sample to set.
    */
   public void setSample(String sample)
   {
      this.sample = sample;
   }

   /**
    * Gets the quantity in stock.
    *
    * @return Returns a int containing the quantity.
    */
   public int getQuantity()
   {
      return quantity;
   }

   /**
    * Sets the quantity in stock.
    *
    * @param quantity
    *           The int containing the quantity to set.
    */
   public void setQuantity(int quantity)
   {
      this.quantity = quantity;
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
    * Deserializes a JSON string into an Cd object.
    * 
    * @param Json
    *           - the JSON string to inflate.
    * @return A Cd object on success or null on failure.
    */
   public static Cd createObject(String Json)
   {
      ObjectMapper objectMapper = new ObjectMapper();
      try
      {
         Cd cd = objectMapper.readValue(Json, Cd.class);
         return (cd);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return (null);
      }
   }

} // Class
