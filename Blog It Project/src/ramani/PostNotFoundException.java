package ramani;

public class PostNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925140513345330812L;

	/**
	 * 
	 */
	public PostNotFoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public PostNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PostNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PostNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
