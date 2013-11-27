package com.shunix.shunixdevkit.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author Ray WANG
 * @since Nov 15th, 2013
 * @version 1.0.0
 */
public class HttpGetJson {
	private String requestUrl;
	private DefaultHttpClient httpClient;
	private String responseJson;
	private HttpGet httpGet;
	private HttpResponse httpResponse;

	public HttpGetJson(String requestUrl) {
		this.requestUrl = requestUrl;
		this.httpClient = new DefaultHttpClient();
		this.responseJson = null;
		this.httpGet = new HttpGet(this.requestUrl);
		this.httpResponse = null;
	}

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @return the responseJson
	 */
	public String getResponseJson() {
		return responseJson;
	}

	/**
	 * @throws IOException
	 * @throws ClientProtocolException
	 * 
	 */
	public boolean execute() throws ClientProtocolException, IOException {
		httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			return false;
		} else {
			responseJson = EntityUtils.toString(httpResponse.getEntity());
		}
		return true;
	}
}
