package ramani;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925140513345330812L;

	/**
	 * 
	 */
	public UserNotFoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
