package com.coolworks.application;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.coolworks.humanpyramid.algorithm.FormulaBasedHumanEdgeWeightStrategy;
import com.coolworks.humanpyramid.algorithm.HumanEdgeWeightStrategy;
import com.coolworks.humanpyramid.controller.HttpHumanEdgeWeightHandler;
import com.coolworks.humanpyramid.service.HumanEdgeWeightService;
import com.coolworks.netty.http.HttpHandlerMapping;
import com.coolworks.netty.http.HttpRequestHandler;
import com.coolworks.netty.http.server.HttpServer;
import com.coolworks.netty.http.server.HttpServerInitializer;

@Configuration
@ComponentScan("com.coolworks")
@PropertySource("classpath:server.properties")
public class ApplicationConfiguration {

	@Autowired 
	private ApplicationContext applicationContext;
	
	@Value("${server.port:8080}")
	private Integer serverPort;
	
	@Resource(name = "mapping.properties")
	private Properties someConfig;
	
	@Bean
	public HttpServer httpServer(ServerBootstrap serverBootstrap) {
		return new HttpServer(serverBootstrap, serverPort);
	}
	
	@Bean
	public ServerBootstrap serverBootstrap(EventLoopGroup bossGroup, EventLoopGroup workerGroup, HttpServerInitializer httpServerInitializer) {
		return new ServerBootstrap()
    	.option(ChannelOption.SO_BACKLOG, 1024)
    	.group(bossGroup, workerGroup)
    	.channel(NioServerSocketChannel.class)
    	.handler(new LoggingHandler(LogLevel.INFO))
    	.childHandler(httpServerInitializer);
	}
	
	@Bean
	public EventLoopGroup bossGroup() {
		return new NioEventLoopGroup(1);
	}

	@Bean
	public EventLoopGroup workerGroup() {
		return new NioEventLoopGroup();
	}
	
	@Bean
	public HttpServerInitializer httpServerInitializer(HttpHandlerMapping handlerMapping) {
		return new HttpServerInitializer(handlerMapping);
	}
	
	@Bean
	public HttpHandlerMapping httpHandlerMapping() throws BeansException, ClassNotFoundException {
	    return new HttpHandlerMapping(getPathToHttpRequestHandlerMap());
	}
	
	@Bean
	public HttpHumanEdgeWeightHandler httpHumanEdgeWeightHandler(HumanEdgeWeightService humanEdgeWeightService) {
		return new HttpHumanEdgeWeightHandler(humanEdgeWeightService);
	}
	
	@Bean
	public HumanEdgeWeightService humanEdgeWeightService(HumanEdgeWeightStrategy humanEdgeWeightStrategy) {
		return new HumanEdgeWeightService(humanEdgeWeightStrategy);
	}
	
	@Bean
	public HumanEdgeWeightStrategy humanEdgeWeightStrategy() {
		return new FormulaBasedHumanEdgeWeightStrategy();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "mapping.properties")
	public PropertiesFactoryBean mapper() {
	    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
	    propertiesFactoryBean.setLocation(new ClassPathResource("mapping.properties"));
	    return propertiesFactoryBean;
	}
	
	private Map<String, HttpRequestHandler> getPathToHttpRequestHandlerMap() throws BeansException, ClassNotFoundException {
		Map<String, HttpRequestHandler> pathToRequestHandlerMap = new HashMap<>();
		for (Map.Entry<String, Class<?>> entry : getPathToClassMap().entrySet()) {
			pathToRequestHandlerMap.put(entry.getKey(), (HttpRequestHandler) applicationContext.getBean(entry.getValue()));
		}
		return pathToRequestHandlerMap;
	}
	
	private Map<String, Class<?>> getPathToClassMap() throws ClassNotFoundException {
		Map<String, Class<?>> pathToClassMap = new HashMap<>();
		for (Map.Entry<String, String> entry : getPathToClassNameMap().entrySet()) {
			pathToClassMap.put(entry.getKey(), Class.forName(entry.getValue()));
		}
		return pathToClassMap;
	}
	
	private Map<String, String> getPathToClassNameMap() {
		return someConfig.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
	}
	
}
