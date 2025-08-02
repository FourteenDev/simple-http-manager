package dev.Fourteen.SimpleHttpManager.Examples;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.Fourteen.SimpleHttpManager.HttpException;
import dev.Fourteen.SimpleHttpManager.HttpManager;
import dev.Fourteen.SimpleHttpManager.Clients.HttpClientConfig;
import dev.Fourteen.SimpleHttpManager.Entities.HttpRequest;
import dev.Fourteen.SimpleHttpManager.Entities.HttpResponse;
import dev.Fourteen.SimpleHttpManager.Enums.HttpMethod;

/**
 * Example class demonstrating how to use the HTTP Manager system.
 */
public class HttpManagerExample
{
	private static final Logger logger = LoggerFactory.getLogger(HttpManagerExample.class);

	public static void main(String[] args)
	{
		// Example 1: Basic HTTP Manager usage
		basicUsageExample();

		// Example 2: Custom configuration
		customConfigurationExample();

		// Example 3: Error handling
		errorHandlingExample();

		// Example 4: Advanced usage with custom requests
		advancedUsageExample();
	}

	public static void basicUsageExample()
	{
		logger.info("=== Basic HTTP Manager Usage Example ===");

		try {
			// Get the singleton instance
			HttpManager httpManager = HttpManager.getInstance();

			// Simple GET request
			HttpResponse response = httpManager.get("https://httpbin.org/get");
			logger.info("GET Response Status: {}", response.getStatusCode());
			logger.info("GET Response Body: {}", response.getBody().substring(0, Math.min(100, response.getBody().length())));

			// Simple POST request with JSON
			JSONObject postData = new JSONObject();
			postData.put("name", "John Doe");
			postData.put("email", "john@example.com");

			HttpResponse postResponse = httpManager.post("https://httpbin.org/post", postData);
			logger.info("POST Response Status: {}", postResponse.getStatusCode());
		} catch (HttpException e) {
			logger.error("HTTP request failed", e);
		}
	}

	public static void customConfigurationExample()
	{
		logger.info("=== Custom Configuration Example ===");

		try {
			// Create custom configuration
			HttpClientConfig config = new HttpClientConfig()
				.connectionTimeout(5000) // 5 seconds
				.readTimeout(15000) // 15 seconds
				.maxConnections(10)
				.maxRetries(2)
				.retryDelay(java.time.Duration.ofSeconds(2))
				.userAgent("CustomApp/1.0");

			// Get instance with custom configuration
			HttpManager httpManager = HttpManager.getInstance(config);

			// Add custom headers
			httpManager.addDefaultHeader("X-Custom-Header", "CustomValue");

			// Make request with custom configuration
			HttpResponse response = httpManager.get("https://httpbin.org/headers");
			logger.info("Custom Config Response Status: {}", response.getStatusCode());
		} catch (HttpException e) {
			logger.error("HTTP request failed", e);
		}
	}

	public static void errorHandlingExample()
	{
		logger.info("=== Error Handling Example ===");

		try {
			HttpManager httpManager = HttpManager.getInstance();

			// This will likely fail (invalid URL)
			@SuppressWarnings("unused")
			HttpResponse response = httpManager.get("https://invalid-url-that-does-not-exist.com");
		} catch (HttpException e) {
			logger.error("HTTP request failed with status: {}", e.getStatusCode());
			logger.error("Failed URL: {}", e.getUrl());
			logger.error("Error message: {}", e.getMessage());

			// Handle different types of errors
			if (e.hasStatusCode())
			{
				if (e.getStatusCode() >= 400 && e.getStatusCode() < 500)
					logger.error("Client error occurred");
				else if (e.getStatusCode() >= 500)
					logger.error("Server error occurred");
			} else {
				logger.error("Network or connection error occurred");
			}
		}
	}

	public static void advancedUsageExample()
	{
		logger.info("=== Advanced Usage Example ===");

		try {
			HttpManager httpManager = HttpManager.getInstance();

			// Create custom request with specific headers and timeout
			HttpRequest customRequest = new HttpRequest()
				.url("https://httpbin.org/anything")
				.method(HttpMethod.POST)
				.header("X-Custom-Header", "CustomValue")
				.header("Authorization", "Bearer your-token-here")
				.body("{\"custom\": \"data\"}")
				.timeout(10000) // 10 seconds
				.followRedirects(false);

			HttpResponse response = httpManager.execute(customRequest);
			logger.info("Custom Request Status: {}", response.getStatusCode());
			logger.info("Response Time: {}ms", response.getResponseTime());

			// Check response details
			if (response.isSuccess())
				logger.info("Request was successful");
			else if (response.isClientError())
				logger.warn("Client error occurred");
			else if (response.isServerError())
				logger.error("Server error occurred");

			// Get specific header from response
			String contentType = response.getHeader("Content-Type");
			logger.info("Response Content-Type: {}", contentType);
		} catch (HttpException e) {
			logger.error("Advanced HTTP request failed", e);
		}
	}

	public static void resourceCleanupExample()
	{
		logger.info("=== Resource Cleanup Example ===");

		HttpManager httpManager = HttpManager.getInstance();

		try {
			// Make some requests
			@SuppressWarnings("unused")
			HttpResponse response1 = httpManager.get("https://httpbin.org/get");
			@SuppressWarnings("unused")
			HttpResponse response2 = httpManager.get("https://httpbin.org/headers");

			logger.info("Made requests successfully");
		} catch (HttpException e) {
			logger.error("Requests failed", e);
		} finally {
			// Always close the HTTP manager when done
			httpManager.close();
			logger.info("HTTP Manager closed and resources released");
		}
	}
}