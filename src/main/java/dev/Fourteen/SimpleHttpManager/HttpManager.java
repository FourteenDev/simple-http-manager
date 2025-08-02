package dev.Fourteen.SimpleHttpManager;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.Fourteen.SimpleHttpManager.Clients.ApacheHttpClient;
import dev.Fourteen.SimpleHttpManager.Clients.HttpClient;
import dev.Fourteen.SimpleHttpManager.Clients.HttpClientConfig;
import dev.Fourteen.SimpleHttpManager.Entities.HttpRequest;
import dev.Fourteen.SimpleHttpManager.Entities.HttpResponse;
import dev.Fourteen.SimpleHttpManager.Enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Main HTTP Manager class that for HTTP operations.
 * Implements the Singleton pattern and Facade pattern for easy access to HTTP functionality.
 */
public class HttpManager
{
	private static final Logger logger = LoggerFactory.getLogger(HttpManager.class);

	private static HttpManager instance;
	private final HttpClient httpClient;
	private final HttpClientConfig config;

	// Default headers for API requests
	private final Map<String, String> defaultHeaders;

	private HttpManager()
	{
		this.config = new HttpClientConfig();
		this.httpClient = new ApacheHttpClient(config);
		this.defaultHeaders = new HashMap<>();
		initializeDefaultHeaders();
	}

	private HttpManager(HttpClientConfig config)
	{
		this.config = config;
		this.httpClient = new ApacheHttpClient(config);
		this.defaultHeaders = new HashMap<>();
		initializeDefaultHeaders();
	}

	/**
	 * Returns the singleton instance of HttpManager with default configuration.
	 *
	 * @return	The HttpManager instance.
	 */
	public static synchronized HttpManager getInstance()
	{
		if (instance == null)
			instance = new HttpManager();
		return instance;
	}

	/**
	 * Returns the singleton instance of HttpManager with custom configuration.
	 *
	 * @param	config	The custom configuration.
	 *
	 * @return			The HttpManager instance.
	 */
	public static synchronized HttpManager getInstance(HttpClientConfig config)
	{
		if (instance == null)
			instance = new HttpManager(config);
		return instance;
	}

	/**
	 * Initializes default headers for API requests.
	 */
	private void initializeDefaultHeaders()
	{
		defaultHeaders.put("Content-Type", "application/json");
		defaultHeaders.put("Accept", "application/json");
		defaultHeaders.put("User-Agent", config.getUserAgent());
	}

	/**
	 * Adds a default header that will be included in all requests.
	 *
	 * @param	key		The header key.
	 * @param	value	The header value.
	 */
	public void addDefaultHeader(String key, String value)
	{
		defaultHeaders.put(key, value);
	}

	/**
	 * Removes a default header.
	 *
	 * @param	key	The header key to remove.
	 */
	public void removeDefaultHeader(String key)
	{
		defaultHeaders.remove(key);
	}

	/**
	 * Clears all default headers.
	 */
	public void clearDefaultHeaders()
	{
		defaultHeaders.clear();
		initializeDefaultHeaders();
	}

	/**
	 * Sends a GET request to the specified URL.
	 *
	 * @param	url				The URL to send the GET request to.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse get(String url) throws HttpException
	{
		logger.debug("Sending GET request to: {}", url);
		return httpClient.get(url);
	}

	/**
	 * Sends a GET request to the specified URL with custom headers.
	 *
	 * @param	url				The URL to send the GET request to.
	 * @param	headers			Additional headers to include.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse get(String url, Map<String, String> headers) throws HttpException
	{
		logger.debug("Sending GET request to: {} with custom headers", url);

		HttpRequest request = new HttpRequest()
			.url(url)
			.method(HttpMethod.GET)
			.headers(defaultHeaders)
			.headers(headers);

		return httpClient.execute(request);
	}

	/**
	 * Sends a POST request to the specified URL with JSON body.
	 *
	 * @param	url				The URL to send the POST request to.
	 * @param	jsonBody		The JSON body as a string.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse post(String url, String jsonBody) throws HttpException
	{
		logger.debug("Sending POST request to: {}", url);
		return httpClient.post(url, jsonBody);
	}

	/**
	 * Sends a POST request to the specified URL with JSON object.
	 *
	 * @param	url				The URL to send the POST request to.
	 * @param	jsonObject		The JSON object to send.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse post(String url, JSONObject jsonObject) throws HttpException
	{
		return post(url, jsonObject.toString());
	}

	/**
	 * Sends a POST request to the specified URL with JSON body and custom headers.
	 *
	 * @param	url				The URL to send the POST request to.
	 * @param	jsonBody		The JSON body as a string.
	 * @param	headers			Additional headers to include.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse post(String url, String jsonBody, Map<String, String> headers) throws HttpException
	{
		logger.debug("Sending POST request to: {} with custom headers", url);

		Map<String, String> allHeaders = new HashMap<>(defaultHeaders);
		allHeaders.putAll(headers);

		return httpClient.post(url, jsonBody, allHeaders);
	}

	/**
	 * Sends a POST request to the specified URL with JSON object and custom headers.
	 *
	 * @param	url				The URL to send the POST request to.
	 * @param	jsonObject		The JSON object to send.
	 * @param	headers			Additional headers to include.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse post(String url, JSONObject jsonObject, Map<String, String> headers) throws HttpException
	{
		return post(url, jsonObject.toString(), headers);
	}

	/**
	 * Sends a PUT request to the specified URL with JSON body.
	 *
	 * @param	url				The URL to send the PUT request to.
	 * @param	jsonBody		The JSON body as a string.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse put(String url, String jsonBody) throws HttpException
	{
		logger.debug("Sending PUT request to: {}", url);
		return httpClient.put(url, jsonBody);
	}

	/**
	 * Sends a PUT request to the specified URL with JSON object.
	 *
	 * @param	url				The URL to send the PUT request to.
	 * @param	jsonObject		The JSON object to send.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse put(String url, JSONObject jsonObject) throws HttpException
	{
		return put(url, jsonObject.toString());
	}

	/**
	 * Sends a DELETE request to the specified URL.
	 *
	 * @param	url				The URL to send the DELETE request to.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse delete(String url) throws HttpException
	{
		logger.debug("Sending DELETE request to: {}", url);
		return httpClient.delete(url);
	}

	/**
	 * Sends a custom HTTP request.
	 *
	 * @param	request			The HTTP request to send.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse execute(HttpRequest request) throws HttpException
	{
		logger.debug("Executing custom HTTP request: {}", request.getMethod() + " " + request.getUrl());

		// Merge default headers with request headers
		Map<String, String> allHeaders = new HashMap<>(defaultHeaders);
		allHeaders.putAll(request.getHeaders());

		HttpRequest finalRequest = new HttpRequest()
			.url(request.getUrl())
			.method(request.getMethod())
			.body(request.getBody())
			.timeout(request.getTimeout())
			.followRedirects(request.isFollowRedirects())
			.headers(allHeaders);

		return httpClient.execute(finalRequest);
	}

	/**
	 * Sends an API request with authentication token.
	 *
	 * @param	url				The API URL.
	 * @param	method			The HTTP method.
	 * @param	jsonBody		The JSON body (can be null for GET/DELETE requests).
	 * @param	authToken		The authentication token.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse sendApiRequest(String url, HttpMethod method, String jsonBody, String authToken) throws HttpException
	{
		logger.debug("Sending API request: {} {}", method, url);

		Map<String, String> headers = new HashMap<>();
		if (authToken != null && !authToken.isEmpty())
			headers.put("Authorization", "Bearer " + authToken);

		HttpRequest request = new HttpRequest()
			.url(url)
			.method(method)
			.body(jsonBody)
			.headers(headers);

		return execute(request);
	}

	/**
	 * Sends an API request with authentication token and JSON object.
	 *
	 * @param	url				The API URL.
	 * @param	method			The HTTP method.
	 * @param	jsonObject		The JSON object (can be null for GET/DELETE requests).
	 * @param	authToken		The authentication token.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	public HttpResponse sendApiRequest(String url, HttpMethod method, JSONObject jsonObject, String authToken) throws HttpException
	{
		String jsonBody = jsonObject != null ? jsonObject.toString() : null;
		return sendApiRequest(url, method, jsonBody, authToken);
	}

	/**
	 * Closes the HTTP manager and release resources.
	 */
	public void close()
	{
		logger.info("Closing HTTP manager");
		if (httpClient != null)
			httpClient.close();
	}

	/**
	 * Returns the current configuration.
	 *
	 * @return	The HTTP client configuration.
	 */
	public HttpClientConfig getConfig()
	{
		return config;
	}

	/**
	 * Returns the underlying HTTP client.
	 *
	 * @return	The HTTP client instance.
	 */
	public HttpClient getHttpClient()
	{
		return httpClient;
	}
}