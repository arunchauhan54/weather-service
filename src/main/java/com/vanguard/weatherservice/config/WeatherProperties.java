package com.vanguard.weatherservice.config;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class WeatherProperties {
	private String weatherMapUrl;
	private String weatherMapApiKey;
	private List<String> apiKeys;
	private int apiKeyTimeWindowInSeconds;
	private int apiKeyAllowedCountWithinTimeWindow;
}
