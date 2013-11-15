package com.shunix.shunixdevkit.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author Ray WANG
 * @since Nov 15th, 2013
 * @version 1.0.0
 */

public class HttpPostJson {
	private String requestUrl;
	private String requestJson;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	private String responseJson;
	private DefaultHttpClient httpClient;

	public HttpPostJson(String requestUrl, String requestJson) {
		this.requestUrl = requestUrl;
		this.httpClient = new DefaultHttpClient();
		this.httpPost = new HttpPost(requestUrl);
		this.responseJson = null;
		this.httpResponse = null;
	}

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @return the requestJson
	 */
	public String getRequestJson() {
		return requestJson;
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
		httpResponse = httpClient.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != 200) {
			return false;
		} else {
			responseJson = EntityUtils.toString(httpResponse.getEntity());
		}
		return true;
	}
}
