package club.seedymusic.exceptions;

public class UserDoesNotExistException extends Exception {
	public UserDoesNotExistException () {
		super("Specified user does not exist.");
	}
}
