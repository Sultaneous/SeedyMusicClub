package club.seedymusic.jpa.exceptions;

public class FailedLoginException extends Exception {
	public FailedLoginException() {
		super("Invalid password or username.");
	}
}
