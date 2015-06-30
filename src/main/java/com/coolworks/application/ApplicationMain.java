package com.coolworks.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.coolworks.netty.http.server.HttpServer;

public class ApplicationMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException  {
		new AnnotationConfigApplicationContext(ApplicationConfiguration.class).getBean(HttpServer.class).start();
	}
	
}
