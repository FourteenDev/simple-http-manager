# Simple HTTP Manager System 

[![](https://jitpack.io/v/FourteenDev/simple-http-manager.svg)](https://jitpack.io/#FourteenDev/simple-http-manager)

A comprehensive Java HTTP client management system. Provides scalable, robust HTTP communication capabilities.

## Prerequisites

- Java 11 or higher
- Maven

## Architecture Overview

The HTTP Manager system is built using several design patterns to ensure maintainability, scalability, and ease of use:

### Design Patterns Used

1. **Singleton Pattern** - `HttpManager` provides a single instance for the entire application.
2. **Strategy Pattern** - `HttpClient` interface allows different HTTP client implementations.
3. **Builder Pattern** - `HttpRequest` and `HttpClientConfig` use builder pattern for easy construction.
4. **Facade Pattern** - `HttpManager` provides a simplified interface to complex HTTP operations.
5. **Factory Pattern** - Easy creation of HTTP clients with different configurations.

### Class Structure

```
SimpleHttpManager/
├── Clients/
	├── HttpClient.java           # Interface for HTTP operations
	├── ApacheHttpClient.java     # Apache HttpClient 5 implementation
	└── HttpClientConfig.java     # Configuration class
├── Entities/
	├── HttpRequest.java          # Request DTO
	└── HttpResponse.java         # Response DTO
├── Enums/
	└── HttpMethod.java           # HTTP methods enum
├── Examples/
	├── ApiService.java           # Service layer for API operations
	└── HttpManagerExample.java   # Usage examples and documentation
├── HttpManager.java              # Main facade class (Singleton)
└── HttpException.java            # Custom exception for HTTP errors
```

## Features

### Core Features

- **Connection Pooling** - Efficient connection management with configurable pool sizes
- **Retry Mechanism** - Automatic retry with exponential backoff
- **Timeout Management** - Configurable connection and read timeouts
- **Header Management** - Default headers and custom header support
- **Error Handling** - Comprehensive error handling with custom exceptions
- **Logging** - Detailed logging for debugging and monitoring
- **JSON Support** - Built-in JSON request/response handling

### Advanced Features

- **Authentication** - Bearer token support for API authentication
- **Redirect Handling** - Configurable redirect following
- **Custom User Agents** - Configurable user agent strings
- **Response Validation** - Helper methods for response status checking
- **Resource Management** - Proper cleanup of HTTP resources

## Quick Start

### Add to your Project

Add the following code to your `pom.xml` file:

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
	<!-- Simple HTTP Manager System -->
	<dependency>
		<groupId>com.github.FourteenDev</groupId>
		<artifactId>simple-http-manager</artifactId>
		<version>v1.0.0</version>
	</dependency>
</dependencies>
```

[More info](https://jitpack.io/#FourteenDev/simple-http-manager)

### Basic Usage

```java
// Get the HTTP manager instance
HttpManager httpManager = HttpManager.getInstance();

// Simple GET request
HttpResponse response = httpManager.get("https://api.example.com/data");

// Simple POST request with JSON
JSONObject data = new JSONObject();
data.put("name", "John Doe");
data.put("email", "john@example.com");

HttpResponse postResponse = httpManager.post("https://api.example.com/users", data);
```

### Custom Configuration

```java
// Create custom configuration
HttpClientConfig config = new HttpClientConfig()
	.connectionTimeout(5000)           // 5 seconds
	.readTimeout(15000)                // 15 seconds
	.maxConnections(20)                // Max 20 connections
	.maxRetries(3)                     // Retry failed requests 3 times
	.retryDelay(Duration.ofSeconds(2)) // Wait 2 seconds between retries
	.userAgent("MyApp/1.0");           // Custom user agent

// Get instance with custom configuration
HttpManager httpManager = HttpManager.getInstance(config);
```

## Configuration

### HTTP Client Configuration

The `HttpClientConfig` class provides comprehensive configuration options:

| Property | Default | Description |
|----------|---------|-------------|
| `connectionTimeout` | 10000ms | Connection establishment timeout |
| `readTimeout` | 30000ms | Response read timeout |
| `maxConnections` | 20 | Maximum total connections |
| `maxConnectionsPerRoute` | 10 | Maximum connections per route |
| `followRedirects` | true | Whether to follow redirects |
| `userAgent` | "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36" | User agent string |
| `enableRetry` | true | Enable automatic retry |
| `maxRetries` | 3 | Maximum retry attempts |
| `retryDelay` | 1 second | Delay between retries |

### Default Headers

The HTTP Manager automatically includes these default headers:

- `Content-Type: application/json`
- `Accept: application/json`
- `User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36`

You can add custom headers:

```java
HttpManager httpManager = HttpManager.getInstance();
httpManager.addDefaultHeader("X-API-Key", "your-api-key");
httpManager.addDefaultHeader("Authorization", "Bearer your-token");
```

## Error Handling

### Custom Exceptions

The system uses `HttpException` for all HTTP-related errors:

```java
try {
	HttpResponse response = httpManager.get("https://api.example.com/data");
	// Process response
} catch (HttpException e) {
	if (e.hasStatusCode())
		logger.error("HTTP error {}: {}", e.getStatusCode(), e.getMessage());
	else
		logger.error("Network error: {}", e.getMessage());
}
```

### Response Validation

The `HttpResponse` class provides helper methods:

```java
HttpResponse response = httpManager.get("https://api.example.com/data");

if (response.isSuccess())
{
	// Process successful response
} else if (response.isClientError()) {
	// Handle client errors (4xx)
} else if (response.isServerError()) {
	// Handle server errors (5xx)
}
```

## Best Practices

### 1. Resource Management

Always close the HTTP manager when done:

```java
HttpManager httpManager = HttpManager.getInstance();
try {
	// Use HTTP manager
} finally {
	httpManager.close();
}
```

### 2. Error Handling

Always handle exceptions properly:

```java
try {
	HttpResponse response = httpManager.get(url);
	// Process response
} catch (HttpException e) {
	logger.error("HTTP request failed", e);
	// Handle error appropriately
}
```

### 3. Configuration

Use appropriate timeouts and retry settings for your use case:

```java
HttpClientConfig config = new HttpClientConfig()
	.connectionTimeout(5000)            // Shorter for fast-fail scenarios
	.readTimeout(30000)                 // Longer for slow APIs
	.maxRetries(3)                      // Retry transient failures
	.retryDelay(Duration.ofSeconds(1)); // Exponential backoff
```

### 4. Logging

The system provides comprehensive logging. Configure your logging level appropriately:

```properties
# In logback.xml or similar
<logger name="dev.PACKAGENAME.PROJECTNAME.HttpManager" level="DEBUG"/>
```

## Dependencies

- Apache HttpClient 5.3.1
- Apache HttpClient Fluent API 5.3.1
- JSON in Java 20250517
- SLF4J API 2.0.11

## Performance Considerations

### Connection Pooling

- Default: 20 total connections, 10 per route
- Configurable based on your needs
- Automatic connection reuse

### Timeout Settings

Configure timeouts based on your API characteristics:

- **Fast APIs**: Lower timeouts (5-10 seconds)
- **Slow APIs**: Higher timeouts (30-60 seconds)
- **Batch operations**: Higher timeouts for large payloads

### Retry Strategy

The retry mechanism helps with transient failures:

- Default: 3 retries with 1-second delay
- Exponential backoff for better reliability
- Configurable retry conditions

## Troubleshooting

### Common Issues

1. **Connection Timeouts**
   - Increase `connectionTimeout` in configuration
   - Check network connectivity
   - Verify API endpoint availability

2. **Read Timeouts**
   - Increase `readTimeout` for slow APIs
   - Check API response times
   - Consider API optimization

3. **Authentication Errors**
   - Verify API token in configuration
   - Check token expiration
   - Ensure proper header format

4. **Rate Limiting**
   - Implement request throttling
   - Add delays between requests
   - Check API rate limits

### Debugging

Enable debug logging for troubleshooting:

```java
// Add debug headers to see what's being sent
httpManager.addDefaultHeader("X-Debug", "true");

// Check response details
HttpResponse response = httpManager.get(url);
logger.debug("Response: {}", response);
```

## Future Enhancements

Potential improvements for the HTTP Manager:

1. **Circuit Breaker Pattern** - Automatic failure detection and recovery
2. **Request/Response Interceptors** - Custom processing hooks
3. **Metrics Collection** - Performance monitoring
4. **Async Support** - Non-blocking HTTP operations
5. **Caching** - Response caching for repeated requests
6. **Compression** - Automatic request/response compression

## Support

For issues or questions about the HTTP Manager:

1. Check the example code in `HttpManagerExample.java`
2. Review the logging output for error details
3. Verify configuration settings
4. Test with simple requests first

## Run Examples

#### 1. Install dependencies:
```bash
mvn clean install
```

#### 2. Run the examples using Maven:
```bash
mvn compile exec:java -Dexec.mainClass="dev.Fourteen.SimpleHttpManager.Examples.HttpManagerExample"
```
