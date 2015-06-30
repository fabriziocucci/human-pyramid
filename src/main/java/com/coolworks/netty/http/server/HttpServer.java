package com.coolworks.netty.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

public class HttpServer {
	
	private final ServerBootstrap serverBootstrap;
	private final Integer serverPort;
	
	private Channel channel;
	
	public HttpServer(ServerBootstrap serverBootstrap, Integer serverPort) {
        this.serverBootstrap = serverBootstrap;
        this.serverPort = serverPort;
	}
    
    public void start() throws InterruptedException {
    	this.channel = this.serverBootstrap.bind(serverPort).sync().channel();
    }
    
    public void stop() throws InterruptedException {
    	this.channel.close();
    	this.serverBootstrap.group().shutdownGracefully();
    	this.serverBootstrap.childGroup().shutdownGracefully();
    }
    
}
