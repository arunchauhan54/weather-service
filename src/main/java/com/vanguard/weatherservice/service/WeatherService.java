package com.vanguard.weatherservice.service;

import com.vanguard.weatherservice.client.WeatherClient;
import com.vanguard.weatherservice.config.WeatherProperties;
import com.vanguard.weatherservice.dao.entity.WeatherEntity;
import com.vanguard.weatherservice.dao.repository.WeatherRepository;
import com.vanguard.weatherservice.model.Weather;
import com.vanguard.weatherservice.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeatherService {

	public static final String DEFAULT_ERROR_RESPONSE = "no data available";

	private final WeatherClient weatherClient;
	private final WeatherProperties weatherProperties;
	private final WeatherRepository weatherRepository;


	public WeatherService(final WeatherClient weatherClient, final WeatherProperties weatherProperties,
						  final WeatherRepository weatherRepository) {
		this.weatherClient = weatherClient;
		this.weatherProperties = weatherProperties;
		this.weatherRepository = weatherRepository;
	}

	public String getWeather(final String city, final String country) {
		final String query = city + "," + country;
		log.info("making request for: " + query);

		final String description = weatherRepository.findById(query)
				.map(WeatherEntity::getDescription)
				.orElseGet(() -> getWeatherFromThirdParty(query));


		log.info("returning response: " + description);
		return description;
	}

	private String getWeatherFromThirdParty(final String query) {
		final WeatherResponse weatherResponse = weatherClient.getWeather(query,
				weatherProperties.getWeatherMapApiKey());

		final String response = weatherResponse.getWeather()
				.stream()
				.findFirst()
				.map(Weather::getDescription)
				.orElseGet(() -> DEFAULT_ERROR_RESPONSE);

		if (!DEFAULT_ERROR_RESPONSE.equals(response)) {
			final WeatherEntity entity = new WeatherEntity();
			entity.setSearchKey(query);
			entity.setDescription(response);
			weatherRepository.save(entity);
		}

		return response;
	}
}
