package club.seedymusic.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import club.seedymusic.exceptions.ProcessorException;
import club.seedymusic.util.ConfigurationManager;

/**
 * <h2>CurrencyTools Class</h2>
 * <p>
 * A collection of static methods providing common tools to the processor package.
 * 
 * @author Karim Sultan
 * 
 * @version Nov 27, 2017 Created this class.
 */
public class CurrencyTools
{
   /**
    * The name of the configuration file.
    */
   private static final String CONFIG_FILE = "processor.configuration.properties";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_XBT = "currencytools.url_cad_xbt";

   /**
    * Calls blockchain.info and converts a CAD value to XBT. The API is just a GET web call.
    * 
    * @param cad
    *           The CAD value to convert to XBT
    * @return A double containing the converted bitcoin value
    * @throws ProcessorException
    *            Thrown on network failures
    */
   public static double getValueXBT(double cad) throws ProcessorException
   {
      // Sanity
      if (cad < 0) throw (new ProcessorException(
               "Invalid CAD value [" + cad + "], cannot convert it to XBT"));

      // Holds result of web call
      String result = null;

      // Return value
      double xbt = 0;

      try
      {
         ConfigurationManager configurationManager = new ConfigurationManager(CONFIG_FILE);
         String api = configurationManager.getConfiguration(CONFIG_XBT, null);

         if (api == null) throw (new Exception("Could not load API configuration key."));

         api = String.format(api, cad);
         result = doApiCall(api);

         // Convert to double
         xbt = Double.parseDouble(result);

         // TEMP
         System.out.println("RECEIVED: " + result);
      }
      catch (Exception e)
      {
         throw (new ProcessorException(e.getMessage()));
      }

      return (xbt);
   }

   /**
    * Connects to an HTTPS URL and retrieves the result.
    * 
    * @param apiUrl
    *           The URL to connect to for the API; must be HTTPS
    * @return The string content body of the response.
    * @throws MalformedURLException
    *            On bad URL or connection
    * @throws IOException
    *            On network read failure
    */
   private static String doApiCall(String apiUrl) throws MalformedURLException, IOException
   {
      String result;
      // Create HTTPS URL for value and connect
      URL url = new URL(apiUrl);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

      // Create a buffered reader
      InputStream inputStream = connection.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      // Read result
      result = bufferedReader.readLine();
      bufferedReader.close();
      return result;
   }
}
