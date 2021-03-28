package com.vanguard.weatherservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.vanguard.weatherservice.client.WeatherClient;
import com.vanguard.weatherservice.config.WeatherProperties;
import com.vanguard.weatherservice.dao.entity.WeatherEntity;
import com.vanguard.weatherservice.dao.repository.WeatherRepository;
import com.vanguard.weatherservice.model.Weather;
import com.vanguard.weatherservice.model.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

	private WeatherService weatherService;

	@Mock
	private WeatherClient weatherClient;

	@Mock
	private WeatherProperties weatherProperties;

	@Mock
	private WeatherRepository weatherRepository;

	@BeforeEach
	void setUp() {
		weatherService = new WeatherService(weatherClient, weatherProperties, weatherRepository);
	}

	@Test
	@DisplayName("for valid input getWeather return valid response")
	void givenValidData_whenGetWeather_thenWeatherServiceReturnsValidResponse() {

		final String city = "Melbourne";
		final String country = "AU";
		final String description = "clear sky";
		final String apiKey = "dummyKey";
		final String location = city + "," + country;

		final WeatherResponse weatherResponse = new WeatherResponse();
		final Weather weather = new Weather();
		weather.setDescription(description);
		weatherResponse.setWeather(List.of(weather));

		when(weatherProperties.getWeatherMapApiKey()).thenReturn(apiKey);


		when(weatherClient.getWeather(location, apiKey))
				.thenReturn(weatherResponse);

		final String descriptionResponse = weatherService.getWeather(city, country);

		assertEquals(description, descriptionResponse);
	}

	@Test
	@DisplayName("if weather information is available in database then no call to third party")
	void givenRequestedLoactionIsAvailableInDb_whenGetWeather_thenWeatherServiceGetsDataFromDb() {

		final String city = "Melbourne";
		final String country = "AU";
		final String description = "clear sky";
		final String apiKey = "dummyKey";
		final String location = city + "," + country;

		final WeatherEntity weatherEntity = new WeatherEntity();
		weatherEntity.setSearchKey(location);
		weatherEntity.setDescription(description);

		when(weatherRepository.findById(location)).thenReturn(Optional.of(weatherEntity));

		final String descriptionResponse = weatherService.getWeather(city, country);

		verify(weatherClient, times(0)).getWeather(location, apiKey);

		assertEquals(description, descriptionResponse);
	}


	@Test
	@DisplayName("if no weather information then default error description is returned")
	void givenValidData_whenGetWeatherReturnNoReport_thenWeatherServiceReturnsDefaultResponse() {

		final String city = "Melbourne";
		final String country = "AU";
		final String apiKey = "dummyKey";
		final String location = city + "," + country;

		final WeatherResponse weatherResponse = new WeatherResponse();
		weatherResponse.setWeather(Collections.emptyList());

		when(weatherProperties.getWeatherMapApiKey()).thenReturn(apiKey);

		when(weatherClient.getWeather(location, apiKey))
				.thenReturn(weatherResponse);

		final String descriptionResponse = weatherService.getWeather(city, country);

		assertEquals(WeatherService.DEFAULT_ERROR_RESPONSE, descriptionResponse);
	}


}
