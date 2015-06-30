package com.coolworks.netty.http.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coolworks.netty.http.HttpHandlerMapping;
import com.coolworks.netty.http.HttpRequestHandler;

public class HttpDispatcherHandler extends SimpleChannelInboundHandler<HttpRequest> {
    
	private static final Logger LOGGER = LogManager.getLogger(HttpDispatcherHandler.class);
	
    private final HttpHandlerMapping httpHandlerMapping;
    
    public HttpDispatcherHandler(HttpHandlerMapping httpHandlerMapping) {
    	this.httpHandlerMapping = httpHandlerMapping;
	}

    @Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {
		HttpRequestHandler httpRequestHandler = httpHandlerMapping.getHandler(httpRequest);
		HttpResponse httpResponse = httpRequestHandler.handle(httpRequest);
		ctx.write(httpResponse).addListener(ChannelFutureListener.CLOSE);
	}
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOGGER.error("Closing channel due to unexpected exception:", cause);
        ctx.close();
    }
	
}