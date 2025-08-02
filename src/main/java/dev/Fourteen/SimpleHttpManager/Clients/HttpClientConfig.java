package dev.Fourteen.SimpleHttpManager.Clients;

import java.time.Duration;

/**
 * Configuration class for HTTP client settings.
 * Follows the Builder pattern for easy configuration.
 */
public class HttpClientConfig
{
	private int connectionTimeout;
	private int readTimeout;
	private int maxConnections;
	private int maxConnectionsPerRoute;
	private boolean followRedirects;
	private String userAgent;
	private boolean enableRetry;
	private int maxRetries;
	private Duration retryDelay;

	/**
	 * Default constructor with default values.
	 */
	public HttpClientConfig()
	{
		// Default values
		this.connectionTimeout = 10000; // 10 seconds
		this.readTimeout = 30000; // 30 seconds
		this.maxConnections = 20;
		this.maxConnectionsPerRoute = 10;
		this.followRedirects = true;
		this.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
		this.enableRetry = true;
		this.maxRetries = 3;
		this.retryDelay = Duration.ofSeconds(1);
	}

	/**
	 * Sets the connection timeout.
	 *
	 * @param	connectionTimeout	The connection timeout in milliseconds.
	 *
	 * @return						This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig connectionTimeout(int connectionTimeout)
	{
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	/**
	 * Sets the read timeout.
	 *
	 * @param	readTimeout	The read timeout in milliseconds.
	 *
	 * @return				This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig readTimeout(int readTimeout)
	{
		this.readTimeout = readTimeout;
		return this;
	}

	/**
	 * Sets the maximum number of connections.
	 *
	 * @param	maxConnections	The maximum number of connections.
	 *
	 * @return					This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig maxConnections(int maxConnections)
	{
		this.maxConnections = maxConnections;
		return this;
	}

	/**
	 * Sets the maximum number of connections per route.
	 *
	 * @param	maxConnectionsPerRoute	The maximum connections per route.
	 *
	 * @return							This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig maxConnectionsPerRoute(int maxConnectionsPerRoute)
	{
		this.maxConnectionsPerRoute = maxConnectionsPerRoute;
		return this;
	}

	/**
	 * Sets whether to follow redirects.
	 *
	 * @param	followRedirects	True to follow redirects, false otherwise.
	 *
	 * @return					This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig followRedirects(boolean followRedirects)
	{
		this.followRedirects = followRedirects;
		return this;
	}

	/**
	 * Sets the user agent string.
	 *
	 * @param	userAgent	The user agent string.
	 *
	 * @return				This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig userAgent(String userAgent)
	{
		this.userAgent = userAgent;
		return this;
	}

	/**
	 * Sets whether to enable retry mechanism.
	 *
	 * @param	enableRetry	True to enable retry, false otherwise.
	 *
	 * @return				This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig enableRetry(boolean enableRetry)
	{
		this.enableRetry = enableRetry;
		return this;
	}

	/**
	 * Sets the maximum number of retries.
	 *
	 * @param	maxRetries	The maximum number of retries.
	 *
	 * @return				This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig maxRetries(int maxRetries)
	{
		this.maxRetries = maxRetries;
		return this;
	}

	/**
	 * Sets the retry delay.
	 *
	 * @param	retryDelay	The retry delay duration.
	 *
	 * @return				This HttpClientConfig instance for chaining.
	 */
	public HttpClientConfig retryDelay(Duration retryDelay)
	{
		this.retryDelay = retryDelay;
		return this;
	}

	/**
	 * Returns the connection timeout.
	 *
	 * @return	The connection timeout in milliseconds.
	 */
	public int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	/**
	 * Returns the read timeout.
	 *
	 * @return	The read timeout in milliseconds.
	 */
	public int getReadTimeout()
	{
		return readTimeout;
	}

	/**
	 * Returns the maximum number of connections.
	 *
	 * @return	The maximum number of connections.
	 */
	public int getMaxConnections()
	{
		return maxConnections;
	}

	/**
	 * Returns the maximum number of connections per route.
	 *
	 * @return	The maximum connections per route.
	 */
	public int getMaxConnectionsPerRoute()
	{
		return maxConnectionsPerRoute;
	}

	/**
	 * Checks if redirects should be followed.
	 *
	 * @return	True if redirects should be followed.
	 */
	public boolean isFollowRedirects()
	{
		return followRedirects;
	}

	/**
	 * Returns the user agent string.
	 *
	 * @return	The user agent string.
	 */
	public String getUserAgent()
	{
		return userAgent;
	}

	/**
	 * Checks if retry mechanism is enabled.
	 *
	 * @return	True if retry is enabled.
	 */
	public boolean isEnableRetry()
	{
		return enableRetry;
	}

	/**
	 * Returns the maximum number of retries.
	 *
	 * @return	The maximum number of retries.
	 */
	public int getMaxRetries()
	{
		return maxRetries;
	}

	/**
	 * Returns the retry delay.
	 *
	 * @return	The retry delay duration.
	 */
	public Duration getRetryDelay()
	{
		return retryDelay;
	}
}