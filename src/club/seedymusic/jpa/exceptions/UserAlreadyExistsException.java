package club.seedymusic.jpa.exceptions;

public class UserAlreadyExistsException extends Exception {
	public UserAlreadyExistsException() {
		super("This user already exists!");
	}
}
