package club.seedymusic.exceptions;
/**
 * 
 *@param UserAlreadyExistsException throws exception if the user name entered during registration already exist in the database and informs the user.
 */
public class UserAlreadyExistsException extends Exception {
	public UserAlreadyExistsException() {
		super("This user already exists!");
	}
}
