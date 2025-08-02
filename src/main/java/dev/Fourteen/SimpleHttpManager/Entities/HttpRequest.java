package dev.Fourteen.SimpleHttpManager.Entities;

import java.util.HashMap;
import java.util.Map;

import dev.Fourteen.SimpleHttpManager.Enums.HttpMethod;

/**
 * Data Transfer Object representing an HTTP request.
 * Follows the Builder pattern for easy construction.
 */
public class HttpRequest
{
	private String url;
	private HttpMethod method;
	@SuppressWarnings("FieldMayBeFinal")
	private Map<String, String> headers;
	private String body;
	private int timeout;
	private boolean followRedirects;

	/**
	 * Default constructor with default values.
	 */
	public HttpRequest()
	{
		this.headers = new HashMap<>();
		this.timeout = 30000; // Default 30 seconds
		this.followRedirects = true;
	}

	/**
	 * Sets the URL for the HTTP request.
	 *
	 * @param	url		The URL to set.
	 *
	 * @return			This HttpRequest instance for chaining.
	 */
	public HttpRequest url(String url)
	{
		this.url = url;
		return this;
	}

	/**
	 * Sets the HTTP method for the request.
	 *
	 * @param	method	The HTTP method to set.
	 *
	 * @return			This HttpRequest instance for chaining.
	 */
	public HttpRequest method(HttpMethod method)
	{
		this.method = method;
		return this;
	}

	/**
	 * Adds a single header to the request.
	 *
	 * @param	key		The header key.
	 * @param	value	The header value.
	 *
	 * @return			This HttpRequest instance for chaining.
	 */
	public HttpRequest header(String key, String value)
	{
		this.headers.put(key, value);
		return this;
	}

	/**
	 * Adds multiple headers to the request.
	 *
	 * @param	headers	Map of headers to add.
	 *
	 * @return			This HttpRequest instance for chaining.
	 */
	public HttpRequest headers(Map<String, String> headers)
	{
		this.headers.putAll(headers);
		return this;
	}

	/**
	 * Sets the request body.
	 *
	 * @param	body	The request body.
	 *
	 * @return			This HttpRequest instance for chaining.
	 */
	public HttpRequest body(String body)
	{
		this.body = body;
		return this;
	}

	/**
	 * Sets the timeout for the request.
	 *
	 * @param	timeout	The timeout in milliseconds.
	 *
	 * @return			This HttpRequest instance for chaining.
	 */
	public HttpRequest timeout(int timeout)
	{
		this.timeout = timeout;
		return this;
	}

	/**
	 * Sets whether to follow redirects.
	 *
	 * @param	followRedirects	True to follow redirects, false otherwise.
	 *
	 * @return					This HttpRequest instance for chaining.
	 */
	public HttpRequest followRedirects(boolean followRedirects)
	{
		this.followRedirects = followRedirects;
		return this;
	}

	/**
	 * Returns the URL of the request.
	 *
	 * @return	The URL
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * Returns the HTTP method of the request.
	 *
	 * @return	The HTTP method
	 */
	public HttpMethod getMethod()
	{
		return method;
	}

	/**
	 * Returns the headers of the request.
	 *
	 * @return	The headers map
	 */
	public Map<String, String> getHeaders()
	{
		return headers;
	}

	/**
	 * Returns the body of the request.
	 *
	 * @return	The request body
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * Returns the timeout of the request.
	 *
	 * @return	The timeout in milliseconds
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * Checks if the request should follow redirects.
	 *
	 * @return	True if redirects should be followed
	 */
	public boolean isFollowRedirects()
	{
		return followRedirects;
	}

	@Override
	public String toString()
	{
		return "HttpRequest{" +
			"url='" + url + '\'' +
			", method=" + method +
			", headers=" + headers +
			", body='" + body + '\'' +
			", timeout=" + timeout +
			", followRedirects=" + followRedirects +
			'}';
	}
}