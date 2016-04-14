package bg.jwd.webbank.exceptions;

public final class CurrencyConversionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CurrencyConversionException(String message) {
		super(message);
	}
}
