package com.coolworks.netty.http;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public interface HttpRequestHandler {

	public HttpResponse handle(HttpRequest httpRequest);
	
}
