package bg.jwd.webbank.exceptions;

public final class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DaoException(String message) {
		super(message);
	}
}
