package com.coolworks.netty.http.server;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.coolworks.application.ApplicationConfiguration;
import com.coolworks.netty.http.server.HttpServer;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

@RunWith(Parameterized.class)
public class HttpServerTest {
	
	private static final HttpServer httpServer = new AnnotationConfigApplicationContext(ApplicationConfiguration.class).getBean(HttpServer.class);
	
	private static final String VALID_URL = "http://localhost:8080/humanEdgeWeight";
	private static final String INVALID_URL = "http://localhost:8080/crytekRules";
	private static final String LEVEL_FIELD = "level";
	private static final String INDEX_FIELD = "index";

	@Parameters(name = "{0} (level={3} index={4})")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "Test respsonse when URL is invalid", HttpMethod.GET, INVALID_URL, "", "", HttpResponseStatus.NOT_FOUND },
				{ "Test respsonse when level and index are missing", HttpMethod.GET, VALID_URL, "", "", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when level is valid and index is missing", HttpMethod.GET, VALID_URL, "0", "", HttpResponseStatus.OK },
				{ "Test respsonse when index is valid and level is missing", HttpMethod.GET, VALID_URL, "", "0", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when level and index are valid", HttpMethod.GET, VALID_URL, "0", "0", HttpResponseStatus.OK },
				{ "Test respsonse when HTTP method is not allowed", HttpMethod.POST, VALID_URL, "0", "0", HttpResponseStatus.METHOD_NOT_ALLOWED },
				{ "Test respsonse when level is not a number", HttpMethod.GET, VALID_URL, "0a", "0", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when index is not a number", HttpMethod.GET, VALID_URL, "0", "0a", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when level is negative", HttpMethod.GET, VALID_URL, "-1", "0", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when index is negative", HttpMethod.GET, VALID_URL, "0", "-1", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when level is too big", HttpMethod.GET, VALID_URL, Integer.toString(Integer.MAX_VALUE + 1), "0", HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when index is too big", HttpMethod.GET, VALID_URL, "0", Integer.toString(Integer.MAX_VALUE + 1), HttpResponseStatus.BAD_REQUEST },
				{ "Test respsonse when index is greater than level", HttpMethod.GET, VALID_URL, "0", "1", HttpResponseStatus.BAD_REQUEST }
		});
	}
	
	@Parameter(value = 0)
	public String testDescription;
	
	@Parameter(value = 1)
	public HttpMethod httpMethod;
	
	@Parameter(value = 2)
    public String urlAsString;
    
    @Parameter(value = 3)
    public String levelAsString;
    
    @Parameter(value = 4)
    public String indexAsString;
	
    @Parameter(value = 5)
    public HttpResponseStatus expectedResponseStatus;
    
	@BeforeClass
	public static void setUp() throws InterruptedException {
		httpServer.start();
	}
	
	@AfterClass
	public static void tearDown() throws InterruptedException {
		httpServer.stop();
	}
	
	@Test
	public void testByRequest() throws UnirestException {
		
		HttpRequest getRequest = new HttpRequest(httpMethod, urlAsString);
		if (!levelAsString.isEmpty()) {
			getRequest.queryString(LEVEL_FIELD, levelAsString);
		}
		if (!indexAsString.isEmpty()) {
			getRequest.queryString(INDEX_FIELD, indexAsString);
		}
		
		HttpResponse<String> httpResponse = getRequest.asString();
		Assert.assertEquals(expectedResponseStatus.code(), httpResponse.getStatus());
	}
	
}
