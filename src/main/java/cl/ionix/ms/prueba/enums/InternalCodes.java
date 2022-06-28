package cl.ionix.ms.prueba.enums;

/**
 * Internal errors constants enum
 */
public enum InternalCodes {

	REQUIRED_DATA_EMPTY(1, "Campos obligatorios vacios", 400),
	REQUIRED_HEADERS_EMPTY(2, "Headers obligatorios vacios", 400),
	REQUIRED_ID(2, "ID vacio o nulo", 400),
    USER_NOT_AUTHORIZED(3, "Usuario no autorizado", 401),
    FORBIDDEN(4, "No tiene permisos para acceder a la informacion", 403),
    USER_NOT_FOUND(5, "No se encuentran datos", 404),
    INTERNAL_SERVER_ERROR(4, "Acceso invalido al recurso", 500),
    SERVICE_UNAVAILABLE(5, "Servicio no disponible", 503),
	GATEWAY_TIMEOUT(6, "Tiempo de respuesta mayor a lo esperado", 504),
	SQL_EXCEPTION(7, "Error en motor de base de datos", 404);

	private final int value;
	private final String reasonPhrase;
	private final int httpStatusCode;

	/**
	 * Constructor
	 * @param value
	 * @param reasonPhrase
	 * @param httpStatusCode
	 */
	InternalCodes(int value, String reasonPhrase, int httpStatusCode) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * Returns the value
	 * @return {@link String} The object's string
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Returns the reason phrase
	 * @return {@link String} The error's reason phrase
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	/**
	 * Returns the object's String
	 * @return {@link String} The error's string
	 */
	@Override
	public String toString() {
		return this.value + " " + name();
	}

	/**
	 * Returns the HTTP status code
	 * @return {@link int} The error's HTTP status code
	 */
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

}