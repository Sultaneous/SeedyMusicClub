package club.seedymusic.exceptions;

public class UserAlreadyExistsException extends Exception {
	public UserAlreadyExistsException() {
		super("This user already exists!");
	}
}
