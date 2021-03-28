package com.vanguard.weatherservice.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import com.vanguard.weatherservice.config.WeatherProperties;
import com.vanguard.weatherservice.exception.ServiceAccessException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RateLimiter {

	private final WeatherProperties properties;
	private final Map<String, AccessRecord> apiKeyAccessRecord = new ConcurrentHashMap<>();

	public RateLimiter(final WeatherProperties properties) {
		this.properties = properties;
	}

	@PostConstruct
	public void init() {
		final List<String> apiKeys = properties.getApiKeys();
		apiKeys.forEach(key ->
				apiKeyAccessRecord.put(key, AccessRecord.builder()
						.count(0)
						.time(LocalDateTime.now())
						.build()));
	}

	public boolean isAllowed(final String apiKey) throws ServiceAccessException {

		if (Objects.isNull(apiKey) || !apiKeyAccessRecord.containsKey(apiKey)) {
			throw new ServiceAccessException("access not allowed", HttpStatus.FORBIDDEN);
		}
		final int allowedDuration = properties.getApiKeyTimeWindowInSeconds();
		final int allowedCount = properties.getApiKeyAllowedCountWithinTimeWindow();

		apiKeyAccessRecord.computeIfPresent(apiKey,
				(key, record) -> {
					log.info("current record: " + record);
					if (Duration.between(record.getTime(), LocalDateTime.now()).toSeconds() > allowedDuration) {
						return AccessRecord.builder()
								.time(LocalDateTime.now())
								.count(1)
								.build();
					} else {
						if (record.getCount() < allowedCount) {
							return AccessRecord.builder()
									.count(record.getCount() + 1)
									.time(record.getTime())
									.build();
						} else {
							throw new ServiceAccessException(
									"rejected because of rate limit", HttpStatus.TOO_MANY_REQUESTS);
						}
					}

				});
		return true;
	}

}

@Getter
@Builder
@ToString
class AccessRecord {
	private final int count;
	private final LocalDateTime time;
}
