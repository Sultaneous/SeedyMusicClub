package club.seedymusic.exceptions;
/**
 * 
 @param FailedLoginException throws an exception if the user name and password don't match and informs the user.
 */
public class FailedLoginException extends Exception {
	public FailedLoginException() {
		super("Invalid password or username.");
	}
}
