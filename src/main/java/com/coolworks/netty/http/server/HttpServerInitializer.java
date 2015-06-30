package com.coolworks.netty.http.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import com.coolworks.netty.http.HttpHandlerMapping;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

	private final HttpHandlerMapping httpHandlerMapping;
	
    public HttpServerInitializer(HttpHandlerMapping httpHandlerMapping) {
		this.httpHandlerMapping = httpHandlerMapping;
	}

	@Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpDispatcherHandler(httpHandlerMapping));
    }

}
