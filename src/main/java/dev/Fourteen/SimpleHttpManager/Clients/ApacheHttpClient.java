package dev.Fourteen.SimpleHttpManager.Clients;

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.Fourteen.SimpleHttpManager.HttpException;
import dev.Fourteen.SimpleHttpManager.Entities.HttpRequest;
import dev.Fourteen.SimpleHttpManager.Entities.HttpResponse;
import dev.Fourteen.SimpleHttpManager.Enums.HttpMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Apache HttpClient 5 implementation of the HttpClient interface.
 */
public class ApacheHttpClient implements HttpClient
{
	private static final Logger logger = LoggerFactory.getLogger(ApacheHttpClient.class);

	private final CloseableHttpClient httpClient;
	private final HttpClientConfig config;
	private final RetryHandler retryHandler;

	/**
	 * Default constructor with default configuration.
	 */
	public ApacheHttpClient()
	{
		this(new HttpClientConfig());
	}

	/**
	 * Constructor with custom configuration.
	 *
	 * @param	config	The HTTP client configuration.
	 */
	public ApacheHttpClient(HttpClientConfig config)
	{
		this.config = config;
		this.retryHandler = new RetryHandler(config);
		this.httpClient = createHttpClient();
	}

	/**
	 * Creates and configures the HTTP client with connection pooling and request configuration.
	 *
	 * @return	The configured CloseableHttpClient instance.
	 */
	private CloseableHttpClient createHttpClient()
	{
		// Create connection manager with pooling
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(config.getMaxConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerRoute());

		// Create request config
		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(Timeout.ofMilliseconds(config.getConnectionTimeout()))
			.setResponseTimeout(Timeout.ofMilliseconds(config.getReadTimeout()))
			.setRedirectsEnabled(config.isFollowRedirects())
			.build();

		return HttpClients.custom()
			.setConnectionManager(connectionManager)
			.setDefaultRequestConfig(requestConfig)
			.build();
	}

	@Override
	@SuppressWarnings("UseSpecificCatch")
	public HttpResponse execute(HttpRequest request) throws HttpException
	{
		long startTime = System.currentTimeMillis();

		try {
			// Create HTTP method based on request
			HttpUriRequestBase httpMethod = createHttpMethod(request);

			// Add headers
			for (Map.Entry<String, String> header : request.getHeaders().entrySet())
				httpMethod.addHeader(header.getKey(), header.getValue());

			// Add default headers if not present
			if (!request.getHeaders().containsKey("User-Agent"))
				httpMethod.addHeader("User-Agent", config.getUserAgent());

			// Execute with retry logic
			HttpResponse response = retryHandler.executeWithRetry(() -> {
				try {
					return executeRequest(httpMethod);
				} catch (ParseException e) {
					return null;
				}
			});

			// Set response time
			response.setResponseTime(System.currentTimeMillis() - startTime);

			return response;

		} catch (Exception e) {
			long responseTime = System.currentTimeMillis() - startTime;
			logger.error("HTTP request failed for URL: {}", request.getUrl(), e);

			HttpResponse errorResponse = new HttpResponse();
			errorResponse.setResponseTime(responseTime);
			errorResponse.setErrorMessage(e.getMessage());

			throw new HttpException("HTTP request failed: " + e.getMessage(), e);
		}
	}

	/**
	 * Creates the appropriate HTTP method based on the request.
	 *
	 * @param	request		The HTTP request containing method and URL information.
	 *
	 * @return				The configured HttpUriRequestBase instance.
	 * @throws	HttpException	if the HTTP method is not supported.
	 */
	private HttpUriRequestBase createHttpMethod(HttpRequest request) throws HttpException
	{
		String url = request.getUrl();
		HttpMethod method = request.getMethod();

		switch (method)
		{
			case GET:
				return new HttpGet(url);
			case POST:
				HttpPost post = new HttpPost(url);
				if (request.getBody() != null)
					post.setEntity(new StringEntity(request.getBody(), ContentType.APPLICATION_JSON));
				return post;
			case PUT:
				HttpPut put = new HttpPut(url);
				if (request.getBody() != null)
					put.setEntity(new StringEntity(request.getBody(), ContentType.APPLICATION_JSON));
				return put;
			case DELETE:
				return new HttpDelete(url);
			case PATCH:
				HttpPatch patch = new HttpPatch(url);
				if (request.getBody() != null)
					patch.setEntity(new StringEntity(request.getBody(), ContentType.APPLICATION_JSON));
				return patch;
			default:
				throw new HttpException("Unsupported HTTP method: " + method);
		}
	}

	/**
	 * Executes the HTTP request and converts the response to our HttpResponse format.
	 *
	 * @param	httpMethod		The HTTP method to execute.
	 *
	 * @return					The HTTP response in our format.
	 * @throws	IOException		if an I/O error occurs during the request.
	 * @throws	ParseException	if an error occurs during setting HTTP response body.
	 */
	private HttpResponse executeRequest(HttpUriRequestBase httpMethod) throws IOException, ParseException
	{
		try (CloseableHttpResponse response = httpClient.execute(httpMethod))
		{
			HttpResponse httpResponse = new HttpResponse();

			// Set status code
			httpResponse.setStatusCode(response.getCode());

			// Set headers
			Map<String, String> headers = new HashMap<>();
			for (Header header : response.getHeaders())
				headers.put(header.getName(), header.getValue());
			httpResponse.setHeaders(headers);

			// Set body
			if (response.getEntity() != null)
				httpResponse.setBody(EntityUtils.toString(response.getEntity()));

			return httpResponse;
		}
	}

	@Override
	public HttpResponse get(String url) throws HttpException
	{
		HttpRequest request = new HttpRequest()
			.url(url)
			.method(HttpMethod.GET);
		return execute(request);
	}

	@Override
	public HttpResponse post(String url, String body) throws HttpException
	{
		HttpRequest request = new HttpRequest()
			.url(url)
			.method(HttpMethod.POST)
			.body(body);
		return execute(request);
	}

	@Override
	public HttpResponse post(String url, String body, Map<String, String> headers) throws HttpException
	{
		HttpRequest request = new HttpRequest()
			.url(url)
			.method(HttpMethod.POST)
			.body(body)
			.headers(headers);
		return execute(request);
	}

	@Override
	public HttpResponse put(String url, String body) throws HttpException
	{
		HttpRequest request = new HttpRequest()
			.url(url)
			.method(HttpMethod.PUT)
			.body(body);
		return execute(request);
	}

	@Override
	public HttpResponse delete(String url) throws HttpException
	{
		HttpRequest request = new HttpRequest()
			.url(url)
			.method(HttpMethod.DELETE);
		return execute(request);
	}

	@Override
	public void close()
	{
		try {
			if (httpClient != null)
				httpClient.close();
		} catch (IOException e) {
			logger.error("Error closing HTTP client", e);
		}
	}

	/**
	 * Inner class to handle retry logic.
	 */
	private static class RetryHandler
	{
		private final HttpClientConfig config;

		/**
		 * Constructor with configuration for retry behavior.
		 *
		 * @param	config	The HTTP client configuration containing retry settings.
		 */
		public RetryHandler(HttpClientConfig config)
		{
			this.config = config;
		}

		/**
		 * Executes the HTTP request with retry logic based on configuration.
		 *
		 * @param	executor		The HTTP request executor to retry.
		 *
		 * @return					The HTTP response if successful.
		 * @throws	HttpException	if the request fails after all retry attempts.
		 */
		@SuppressWarnings("UseSpecificCatch")
		public HttpResponse executeWithRetry(HttpRequestExecutor executor) throws HttpException
		{
			int attempts = 0;
			Exception lastException = null;

			while (attempts < config.getMaxRetries())
			{
				try {
					return executor.execute();
				} catch (Exception e) {
					lastException = e;
					attempts++;

					if (attempts < config.getMaxRetries() && config.isEnableRetry())
					{
						logger.warn("HTTP request failed, attempt {}/{}: {}", attempts, config.getMaxRetries(), e.getMessage());

						try {
							Thread.sleep(config.getRetryDelay().toMillis());
						} catch (InterruptedException ie) {
							Thread.currentThread().interrupt();
							throw new HttpException("Request interrupted", ie);
						}
					}
				}
			}

			throw new HttpException("HTTP request failed after " + config.getMaxRetries() + " attempts", lastException);
		}
	}

	/**
	 * Functional interface for HTTP request execution.
	 */
	@FunctionalInterface
	private interface HttpRequestExecutor
	{
		/**
		 * Executes the HTTP request.
		 *
		 * @return				The HTTP response.
		 * @throws	IOException	if an I/O error occurs during the request.
		 */
		HttpResponse execute() throws IOException;
	}
}