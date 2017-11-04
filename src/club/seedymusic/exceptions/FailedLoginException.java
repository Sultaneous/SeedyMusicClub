package club.seedymusic.exceptions;
/**
 *@param FailedLoginException
 *                    throws an exception if the username and password don't match and inform the user.*/
public class FailedLoginException extends Exception {
	public FailedLoginException() {
		super("Invalid password or username.");
	}
}
