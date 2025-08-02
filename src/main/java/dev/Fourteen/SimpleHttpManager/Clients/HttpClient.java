package dev.Fourteen.SimpleHttpManager.Clients;

import dev.Fourteen.SimpleHttpManager.HttpException;
import dev.Fourteen.SimpleHttpManager.Entities.HttpRequest;
import dev.Fourteen.SimpleHttpManager.Entities.HttpResponse;

/**
 * Interface defining the contract for HTTP client operations.
 * Follows the Strategy pattern for different HTTP client implementations.
 */
public interface HttpClient
{
	/**
	 * Sends an HTTP request and returns the response.
	 *
	 * @param	request			The HTTP request to send.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	HttpResponse execute(HttpRequest request) throws HttpException;

	/**
	 * Sends a GET request to the specified URL.
	 *
	 * @param	url				The URL to send the GET request to.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	HttpResponse get(String url) throws HttpException;

	/**
	 * Sends a POST request to the specified URL with the given body.
	 *
	 * @param	url				The URL to send the POST request to.
	 * @param	body			The request body.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	HttpResponse post(String url, String body) throws HttpException;

	/**
	 * Sends a POST request to the specified URL with the given body and headers.
	 *
	 * @param	url				The URL to send the POST request to.
	 * @param	body			The request body.
	 * @param	headers			Additional headers to include.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	HttpResponse post(String url, String body, java.util.Map<String, String> headers) throws HttpException;

	/**
	 * Sends a PUT request to the specified URL with the given body.
	 *
	 * @param	url				The URL to send the PUT request to.
	 * @param	body			The request body.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	HttpResponse put(String url, String body) throws HttpException;

	/**
	 * Sends a DELETE request to the specified URL.
	 *
	 * @param	url				The URL to send the DELETE request to.
	 *
	 * @return					The HTTP response.
	 * @throws	HttpException	if an error occurs during the request.
	 */
	HttpResponse delete(String url) throws HttpException;

	/**
	 * Closes the HTTP client and releases any resources.
	 */
	void close();
}