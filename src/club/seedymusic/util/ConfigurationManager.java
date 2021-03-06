package club.seedymusic.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <h2>ConfigurationManager Class</h2>
 * <p>
 * This class is responsible for loading the properties for DAO fields, if the file is available. If
 * the file is not found, it continues with caller provided default values.
 * 
 * @author Karim Sultan
 * 
 * @version Oct 25, 2017 Created this class.
 */
public class ConfigurationManager
{
   /**
    * Stores the properties read from the file.
    */
   private Properties configuration;

   /**
    * 
    * Constructs a new ConfigurationManager.
    * 
    * @param fileName
    *           The configuration file name to load.
    *
    */
   public ConfigurationManager(String fileName)
   {
      try
      {
         configuration = LoadConfiguration(fileName);
      }
      catch (Exception e)
      {
         // Critical failure - BUT we do not crash the app.
         // Instead, we will continue with no properties, and rely on
         // caller provided defaults. This allows the web app to continue
         // even when configuration loading fails.
         configuration = null;
         e.printStackTrace();
      }
   }

   /**
    * Attempts to load the properties from the property file.
    * 
    * @param fileName
    *           The configuration file to load.
    * 
    * @return Returns a Properties object on success.
    * @throws IOException
    *            Throws an IOException on failure.
    */
   private Properties LoadConfiguration(String fileName) throws IOException
   {
      Properties properties = new Properties();
      InputStream inputStream = null;

      try
      {
         // Load the configuration file
         // It is at the root level so we need to determine runtime path
         ClassLoader classLoader = getClass().getClassLoader();
         inputStream = classLoader.getResourceAsStream(fileName);

         // Load the keys
         properties.load(inputStream);

         // Send them back
         return (properties);
      }
      catch (Exception e)
      {
         // Could be IOException, FileNotFoundException, or other.
         // We have no choice but to use a generic catch.

         // File error occurred
         throw (e);
      }
      finally
      {
         // Yes, you have to close an InputStream, because it is really a FileInputStream.
         // And with that, I end a multi-year debate with the Java community.
         // Drop the mic.
         if (inputStream != null) inputStream.close();
      }
   }

   /**
    * Retrieves a configuration property from the configuration property file, based on key name. If
    * the key isn't found, it returns the default value.
    * 
    * @param key
    *           The property name to load from the file.
    * @param defaultValue
    *           The default value to use if property can't be loaded.
    * @return The property value on success, or the default value on failure.
    */
   public String getConfiguration(String key, String defaultValue)
   {
      // If null, it means properties failed to load. Use default value instead.
      if (configuration == null)
         return (defaultValue);

      // Otherwise, try to retrieve the property; if we can't find a value, use default.
      else
         return (configuration.getProperty(key, defaultValue));
   }

} // Class
