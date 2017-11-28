package club.seedymusic.exceptions;

/**
 * <h2>ProcessorException Class</h2>
 * <p>
 * Processor related exceptions throw this exception.
 * 
 * @author Karim Sultan
 * 
 * @version Nov 27, 2017 Created this class.
 */
public class ProcessorException extends Exception
{
   /**
    * Default serial id for serialization
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs a new ProcessorException.
    *
    */
   public ProcessorException()
   {
      super("Unknown Payment Processor Exception.");
   }

   /**
    * Constructs a new ProcessorException with a message.
    *
    * @param message
    *           The error message to encapsulate.
    */
   public ProcessorException(String message)
   {
      super(message);
   }

} // Class
