package club.seedymusic.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager
{
   private static final String CONFIG_FILE = "dao.configuration.properties";

   private Properties configuration;

   public ConfigurationManager()
   {
      try
      {
         configuration = LoadConfiguration();
      }
      catch (IOException e)
      {
         // Critical failure - BUT we do not crash the app.
         // Instead, we will continue with no properties, and rely on
         // caller provided defaults. This allows the web app to continue
         // even when configuration loading fails.
         configuration = null;
         e.printStackTrace();
      }
   }

   private Properties LoadConfiguration() throws IOException
   {
      Properties properties = new Properties();
      InputStream inputStream = null;

      try
      {
         // Load the configuration file
         inputStream = new FileInputStream(CONFIG_FILE);

         // Load the keys
         properties.load(inputStream);

         // Yes, you have to close an InputStream, because it is really a FileInputStream.
         // And with that, I end a multi-year debate with the Java community.
         inputStream.close();

         // Send them back
         return (properties);
      }
      catch (IOException e)
      {
         // File error occurred
         throw (e);
      }
   }

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
