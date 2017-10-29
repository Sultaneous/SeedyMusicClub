package club.seedymusic.exceptions;

public class FailedLoginException extends Exception {
	public FailedLoginException() {
		super("Invalid password or username.");
	}
}
