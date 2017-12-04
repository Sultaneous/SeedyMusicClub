package club.seedymusic.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.seedymusic.exceptions.ProcessorException;
import club.seedymusic.util.ConfigurationManager;

/**
 * <h2>Processor Class</h2>
 * <p>
 * Contains bitcoin payment processor methods.
 * 
 * @author Karim Sultan
 * 
 * @version November 27, 2017 Created this class.
 */
public class Processor
{
   /**
    * The name of the configuration file.
    */
   private static final String CONFIG_FILE = "processor.configuration.properties";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_ADDRESS = "processor.new_address";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_CALLBACK = "processor.callback";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_APIKEY = "processor.api_key";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_PUBLICKEY = "processor.public_key";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_TESTMODE = "processor.test_mode";

   /**
    * Constant representing a key name in the configuration file.
    */
   private static final String CONFIG_TESTADDRESS = "processor.test_address";


   /**
    * 
    * Generates a unique payment address for the buyer to use. Each purchase needs to have a new
    * bitcoin payment address to distinguish them.
    * 
    * @param orderId
    *           The corresponding order id
    * 
    * @return A String address to use for bitcoin payments.
    * @throws ProcessorException
    *            Exception on error.
    */
   public static String getPaymentAddress(int orderId) throws ProcessorException
   {
      try
      {
         // Load our values
         ConfigurationManager configurationManager = new ConfigurationManager(CONFIG_FILE);

         // Test mode values
         boolean testMode = Boolean
                  .valueOf(configurationManager.getConfiguration(CONFIG_TESTMODE, "true"));
         String testAddress = configurationManager.getConfiguration(CONFIG_TESTADDRESS, null);

         if (testMode) return (testAddress);

         // Get the keys
         String apiKey = configurationManager.getConfiguration(CONFIG_APIKEY, null);
         String pubKey = configurationManager.getConfiguration(CONFIG_PUBLICKEY, null);

         // Get the URLS
         String callback = configurationManager.getConfiguration(CONFIG_CALLBACK, null);
         String api = configurationManager.getConfiguration(CONFIG_ADDRESS, null);

         // Format callback
         callback = String.format(callback, orderId, String.valueOf(createNonce()));

         // Format API call
         api = String.format(api, pubKey, callback, apiKey);

         // Make the call
         String response = doApiCall(api);

         // Parse response
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode root = objectMapper.readTree(response);
         String address = root.path("address").asText();

         // NORMALLY the nonce would be cached in the Order record to ensure
         // security BUT in this prototype we aren't implementing the nonce
         // in order to allow for testing. A final production step
         // would be to cache nonce in order record here, and compare it in
         // the callback for extra security.

         // OrderDAO orderDAO = new OrderDAO();
         // orderDAO.setNonce(orderId, nonce);

         // Return the new payment address
         return (address);

      }
      catch (Exception e)
      {
         throw new ProcessorException(e.getMessage());
      }

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

   /**
    * Generates a 32 bit nonce randomly.
    * 
    * @return A 32 bit random integer as a nonce.
    */
   private static int createNonce()
   {
      Random random = new Random();
      return (random.nextInt());
   }

} // Class


