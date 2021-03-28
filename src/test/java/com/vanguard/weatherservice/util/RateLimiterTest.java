package com.vanguard.weatherservice.util;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.IntStream;

import com.vanguard.weatherservice.config.WeatherProperties;
import com.vanguard.weatherservice.exception.ServiceAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RateLimiterTest {

	private RateLimiter rateLimiter;
	@Mock
	private WeatherProperties weatherProperties;

	@Test
	void isAllowed() throws InterruptedException {
		final String key = "key";
		when(weatherProperties.getApiKeys()).thenReturn(List.of(key));
		when(weatherProperties.getApiKeyTimeWindowInSeconds()).thenReturn(3);
		when(weatherProperties.getApiKeyAllowedCountWithinTimeWindow()).thenReturn(5);
		rateLimiter = new RateLimiter(weatherProperties);
		rateLimiter.init();

		// 5 calls will be allowed
		IntStream.rangeClosed(1, 5)
				.forEach(k -> Assertions.assertTrue(rateLimiter.isAllowed(key)));

		// 6th call will throw exception
		Assertions.assertThrows(ServiceAccessException.class, () -> rateLimiter.isAllowed(key));

		// wait for more than 3 seconds for time window to reset
		Thread.sleep(4000);

		// now next 5 call will be allowed
		IntStream.rangeClosed(1, 5)
				.forEach(k -> Assertions.assertTrue(rateLimiter.isAllowed(key)));
	}
}