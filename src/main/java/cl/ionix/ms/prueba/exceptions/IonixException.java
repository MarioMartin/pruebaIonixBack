package cl.ionix.ms.prueba.exceptions;

/**
 * Exceptions control class
 */
public class IonixException extends Exception {

	static final long serialVersionUID = -3387516993124229948L;

	private final int httpCode;

	public IonixException(final String message, final Throwable cause, final int httpCode) {
		super(message, cause);
		this.httpCode = httpCode;
	}

	public IonixException(final String message, final int httpCode) {
		super(message);
		this.httpCode = httpCode;
	}

	public int getHttpCode() {
		return httpCode;
	}

}
