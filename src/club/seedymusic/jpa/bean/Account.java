package club.seedymusic.jpa.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * <h2>Account Class</h2>
 * <p>
 * This class is the entity bean used by the Java Persistence API. It represents a user Account
 * entry in the database.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 9, 2017 Created this class.
 */
@Entity
@Table(name = "accounts")
public class Account
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

   /*
    * Fields
    */

   /**
    * This is the unique key for the object in the database.
    */
   @Id
   @GeneratedValue
   private int id;

   /**
    * The username of the account. Note that this has a unique constraint on it so that no two
    * accounts can have the same username.
    */
   @Column(unique = true)
   private String username;

   /**
    * The password for the account.
    */
   private String password;

   /**
    * The first name of the account owner.
    */
   private String firstName;

   /**
    * The last name of the account owner.
    */
   private String lastName;

   /**
    * The unit number and street name.
    */
   private String street;

   /**
    * The address city.
    */
   private String city;

   /**
    * The address province / state.
    */
   private String province;

   /**
    * The address country.
    */
   private String country;

   /**
    * The address postal code / zip.
    */
   private String postalCode;

   /**
    * The account owner's contact number.
    */
   private String phone;

   /**
    * The email address of the account owner.
    */
   private String email;

   /**
    * Provides a timestamp for date of creation / update.
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date date;

   /*
    * Constructor
    */

   /**
    * Constructs a new Account.
    *
    */
   public Account()
   {
      this.username = DEFAULT_VALUE;
      this.password = DEFAULT_VALUE;
      this.firstName = DEFAULT_VALUE;
      this.lastName = DEFAULT_VALUE;
      this.street = DEFAULT_VALUE;
      this.city = DEFAULT_VALUE;
      this.province = DEFAULT_VALUE;
      this.country = DEFAULT_VALUE;
      this.postalCode = DEFAULT_VALUE;
      this.phone = DEFAULT_VALUE;

      // CHANGED: No longer need to set date; as the
      // DB will set this on creation and on updates
      // this.date = new Date();
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

   /**
    * Gets the username.
    *
    * @return Returns a {@link String} containing the username.
    */
   public String getUsername()
   {
      return username;
   }

   /**
    * Sets the username.
    *
    * @param username
    *           The {@link String} containing the username to set.
    */
   public void setUsername(String username)
   {
      this.username = username;
   }

   /**
    * Gets the password.
    *
    * @return Returns a {@link String} containing the password.
    */
   public String getPassword()
   {
      return password;
   }

   /**
    * Sets the password.
    *
    * @param password
    *           The {@link String} containing the password to set.
    */
   public void setPassword(String password)
   {
      this.password = password;
   }

   /**
    * Gets the firstName.
    *
    * @return Returns a {@link String} containing the firstName.
    */
   public String getFirstName()
   {
      return firstName;
   }

   /**
    * Sets the firstName.
    *
    * @param firstName
    *           The {@link String} containing the firstName to set.
    */
   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   /**
    * Gets the lastName.
    *
    * @return Returns a {@link String} containing the lastName.
    */
   public String getLastName()
   {
      return lastName;
   }

   /**
    * Sets the lastName.
    *
    * @param lastName
    *           The {@link String} containing the lastName to set.
    */
   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   /**
    * Gets the street.
    *
    * @return Returns a {@link String} containing the street.
    */
   public String getStreet()
   {
      return street;
   }

   /**
    * Sets the street.
    *
    * @param street
    *           The {@link String} containing the street to set.
    */
   public void setStreet(String street)
   {
      this.street = street;
   }

   /**
    * Gets the city.
    *
    * @return Returns a {@link String} containing the city.
    */
   public String getCity()
   {
      return city;
   }

   /**
    * Sets the city.
    *
    * @param city
    *           The {@link String} containing the city to set.
    */
   public void setCity(String city)
   {
      this.city = city;
   }

   /**
    * Gets the province.
    *
    * @return Returns a {@link String} containing the province.
    */
   public String getProvince()
   {
      return province;
   }

   /**
    * Sets the province.
    *
    * @param province
    *           The {@link String} containing the province to set.
    */
   public void setProvince(String province)
   {
      this.province = province;
   }

   /**
    * Gets the country.
    *
    * @return Returns a {@link String} containing the country.
    */
   public String getCountry()
   {
      return country;
   }

   /**
    * Sets the country.
    *
    * @param country
    *           The {@link String} containing the country to set.
    */
   public void setCountry(String country)
   {
      this.country = country;
   }

   /**
    * Gets the postalCode.
    *
    * @return Returns a {@link String} containing the postalCode.
    */
   public String getPostalCode()
   {
      return postalCode;
   }

   /**
    * Sets the postalCode.
    *
    * @param postalCode
    *           The {@link String} containing the postalCode to set.
    */
   public void setPostalCode(String postalCode)
   {
      this.postalCode = postalCode;
   }

   /**
    * Gets the phone.
    *
    * @return Returns a {@link String} containing the phone.
    */
   public String getPhone()
   {
      return phone;
   }

   /**
    * Sets the phone.
    *
    * @param phone
    *           The {@link String} containing the phone to set.
    */
   public void setPhone(String phone)
   {
      this.phone = phone;
   }

   /**
    * Gets the email.
    *
    * @return Returns a {@link String} containing the email.
    */
   public String getEmail()
   {
      return email;
   }

   /**
    * Sets the email.
    *
    * @param email
    *           The {@link String} containing the email to set.
    */
   public void setEmail(String email)
   {
      this.email = email;
   }

}
