package com.coolworks.humanpyramid.controller;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.coolworks.humanpyramid.exception.ExceptionMessages;
import com.coolworks.humanpyramid.exception.HttpBadRequestException;
import com.coolworks.humanpyramid.exception.HttpMethodNotAllowedException;
import com.coolworks.humanpyramid.service.HumanEdgeWeightService;
import com.coolworks.netty.http.HttpRequestHandler;

public class HttpHumanEdgeWeightHandler implements HttpRequestHandler {
	
	private static final String LEVEL = "level";
	private static final String INDEX = "index";
	private static final int INDEX_DEFAULT = 0;
	
	private final HumanEdgeWeightService humanEdgeWeightService;
	
	public HttpHumanEdgeWeightHandler(HumanEdgeWeightService humanEdgeWeightService) {
		this.humanEdgeWeightService = humanEdgeWeightService;
	}

	@Override
	public HttpResponse handle(HttpRequest httpRequest) {
		try {
			throwIfHttpMethodIsNotSupported(httpRequest.getMethod());
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.getUri());
			Map<String, List<String>> queryStringParameters = queryStringDecoder.parameters();
			return doHandle(queryStringParameters);
		} catch (HttpMethodNotAllowedException e) {
			return newHttpResponse(HttpResponseStatus.METHOD_NOT_ALLOWED, e.toString());
		} catch (HttpBadRequestException | NumberFormatException e) {
			return newHttpResponse(HttpResponseStatus.BAD_REQUEST, e.toString());
		}
	}

	private HttpResponse doHandle(Map<String, List<String>> queryStringParameters) throws HttpBadRequestException {
		int level = parseInt(LEVEL, queryStringParameters);
		int index = parseOptionalInt(INDEX, queryStringParameters).orElse(INDEX_DEFAULT);
		throwIfParametersAreNotConsistent(level, index);
		return newHttpResponse(HttpResponseStatus.OK, Double.toString(humanEdgeWeightService.getHumanEdgeWeight(level, index)));
	}
	
	private void throwIfHttpMethodIsNotSupported(HttpMethod httpMethod) throws HttpMethodNotAllowedException {
		if (!httpMethod.equals(HttpMethod.GET)) {
			throw new HttpMethodNotAllowedException(ExceptionMessages.HTTP_METHOD_NOT_SUPPORTED.format(httpMethod));
		}
	}
	
	private int parseInt(String parameter, Map<String, List<String>> queryStringParameters) throws HttpBadRequestException {
		
		List<String> parameterValues = queryStringParameters.get(parameter);
		if (parameterValues == null) {
			throw new HttpBadRequestException(ExceptionMessages.MISSING_PARAMETER.format(parameter));
		}
		if (parameterValues.size() > 1) {
			throw new HttpBadRequestException(ExceptionMessages.MULTIPLE_VALUES.format(parameter, parameterValues.size()));
		}
		
		BigInteger parameterAsBigInteger = new BigInteger(parameterValues.get(0));
		if (parameterAsBigInteger.compareTo(BigInteger.ZERO) < 0 || parameterAsBigInteger.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
			throw new HttpBadRequestException(ExceptionMessages.OUT_OF_RANGE_VALUE.format(parameter, parameterAsBigInteger));
		}
		
		return parameterAsBigInteger.intValue();
	}
	
	private Optional<Integer> parseOptionalInt(String parameter, Map<String, List<String>> queryStringParameters) throws HttpBadRequestException {
		return queryStringParameters.containsKey(parameter) ? Optional.of(parseInt(parameter, queryStringParameters)) : Optional.empty();
	}
	
	private void throwIfParametersAreNotConsistent(int level, int index) throws HttpBadRequestException {
		if (index > level) {
			throw new HttpBadRequestException(ExceptionMessages.INCONSISTENT_VALUES.format(INDEX, index, LEVEL, level));
		}
	}
	
	private HttpResponse newHttpResponse(HttpResponseStatus httpResponseStatus, String content) {
		DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, Unpooled.copiedBuffer(content, StandardCharsets.UTF_8));
		defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
		defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH, defaultFullHttpResponse.content().readableBytes());
		return defaultFullHttpResponse;
	}
	
}
