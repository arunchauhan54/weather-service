package com.vanguard.weatherservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanguard.weatherservice.util.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

	private final RateLimiter rateLimiter;

	public RateLimitInterceptor(final RateLimiter rateLimiter) {
		this.rateLimiter = rateLimiter;
	}

	@Override
	public boolean preHandle(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final Object handler) {

		if (request.getRequestURI().equals("/weather")) {
			final String apiKey = request.getHeader("API_KEY");
			rateLimiter.isAllowed(apiKey);
		}

		return true;
	}
}
