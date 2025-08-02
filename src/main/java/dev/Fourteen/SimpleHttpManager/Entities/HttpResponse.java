package dev.Fourteen.SimpleHttpManager.Entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object representing an HTTP response.
 */
public class HttpResponse
{
	private int statusCode;
	private Map<String, String> headers;
	private String body;
	private long responseTime;
	private String errorMessage;

	/**
	 * Default constructor with default values.
	 */
	public HttpResponse()
	{
		this.headers = new HashMap<>();
	}

	/**
	 * Constructor with status code, headers, and body.
	 *
	 * @param	statusCode	The HTTP status code.
	 * @param	headers		The response headers.
	 * @param	body		The response body.
	 */
	public HttpResponse(int statusCode, Map<String, String> headers, String body)
	{
		this.statusCode = statusCode;
		this.headers = headers != null ? headers : new HashMap<>();
		this.body = body;
	}

	/**
	 * Returns the HTTP status code.
	 *
	 * @return	The status code.
	 */
	public int getStatusCode()
	{
		return statusCode;
	}

	/**
	 * Sets the HTTP status code.
	 *
	 * @param	statusCode	The status code to set.
	 */
	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	/**
	 * Returns the response headers.
	 *
	 * @return	The headers map.
	 */
	public Map<String, String> getHeaders()
	{
		return headers;
	}

	/**
	 * Sets the response headers.
	 *
	 * @param	headers	The headers to set.
	 */
	public void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}

	/**
	 * Returns the response body.
	 *
	 * @return	The response body.
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * Sets the response body.
	 *
	 * @param	body	The body to set.
	 */
	public void setBody(String body)
	{
		this.body = body;
	}

	/**
	 * Returns the response time in milliseconds.
	 *
	 * @return	The response time.
	 */
	public long getResponseTime()
	{
		return responseTime;
	}

	/**
	 * Sets the response time in milliseconds.
	 *
	 * @param	responseTime	The response time to set.
	 */
	public void setResponseTime(long responseTime)
	{
		this.responseTime = responseTime;
	}

	/**
	 * Returns the error message.
	 *
	 * @return	The error message.
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param	errorMessage	The error message to set.
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * Checks if the response indicates success.
	 *
	 * @return	True if the response is successful.
	 */
	public boolean isSuccess()
	{
		return statusCode >= 200 && statusCode < 300 && errorMessage == null;
	}

	/**
	 * Checks if the response indicates a client error.
	 *
	 * @return	True if the response is a client error.
	 */
	public boolean isClientError()
	{
		return statusCode >= 400 && statusCode < 500;
	}

	/**
	 * Checks if the response indicates a server error.
	 *
	 * @return	True if the response is a server error.
	 */
	public boolean isServerError()
	{
		return statusCode >= 500 && statusCode < 600;
	}

	/**
	 * Returns a specific header by name.
	 *
	 * @param	name	The header name.
	 *
	 * @return	The header value, or null if not found.
	 */
	public String getHeader(String name)
	{
		return headers.get(name);
	}

	@Override
	public String toString()
	{
		return "HttpResponse{" +
			"statusCode=" + statusCode +
			", headers=" + headers +
			", body='" + (body != null ? body.substring(0, Math.min(body.length(), 100)) + "..." : "null") + '\'' +
			", responseTime=" + responseTime + "ms" +
			", errorMessage='" + errorMessage + '\'' +
			'}';
	}
}