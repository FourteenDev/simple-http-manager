package dev.Fourteen.SimpleHttpManager;

/**
 * Custom exception for HTTP-related errors.
 */
public class HttpException extends Exception
{
	private final int statusCode;
	private final String url;

	/**
	 * Constructor with message only.
	 *
	 * @param	message	The error message.
	 */
	public HttpException(String message)
	{
		super(message);
		this.statusCode = -1;
		this.url = null;
	}

	/**
	 * Constructor with message and cause.
	 *
	 * @param	message	The error message.
	 * @param	cause	The cause of the exception.
	 */
	public HttpException(String message, Throwable cause)
	{
		super(message, cause);
		this.statusCode = -1;
		this.url = null;
	}

	/**
	 * Constructor with message, status code, and URL.
	 *
	 * @param	message		The error message.
	 * @param	statusCode	The HTTP status code.
	 * @param	url			The URL that caused the error.
	 */
	public HttpException(String message, int statusCode, String url)
	{
		super(message);
		this.statusCode = statusCode;
		this.url = url;
	}

	/**
	 * Constructor with message, status code, URL, and cause.
	 *
	 * @param	message		The error message.
	 * @param	statusCode	The HTTP status code.
	 * @param	url			The URL that caused the error.
	 * @param	cause		The cause of the exception.
	 */
	public HttpException(String message, int statusCode, String url, Throwable cause)
	{
		super(message, cause);
		this.statusCode = statusCode;
		this.url = url;
	}

	/**
	 * Returns the HTTP status code.
	 *
	 * @return	The status code, or -1 if not available.
	 */
	public int getStatusCode()
	{
		return statusCode;
	}

	/**
	 * Returns the URL that caused the error.
	 *
	 * @return	The URL, or null if not available.
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * Checks if a status code is available.
	 *
	 * @return	True if status code is available (greater than 0).
	 */
	public boolean hasStatusCode()
	{
		return statusCode > 0;
	}
}