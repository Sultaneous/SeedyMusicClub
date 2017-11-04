package club.seedymusic.exceptions;
/**
 *@Param if the username entered during login process has not been previously registered.
 */
public class UserDoesNotExistException extends Exception {
	public UserDoesNotExistException () {
		super("Specified user does not exist.");
	}
}
