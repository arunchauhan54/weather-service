package com.vanguard.weatherservice.controller;

import com.vanguard.weatherservice.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

	private WeatherController weatherController;

	@Mock
	private WeatherService weatherService;

	@BeforeEach
	void setUp() {
		weatherController = new WeatherController(weatherService);
	}

	@Test
	@DisplayName("for valid input getWeather return valid response")
	void givenValidData_whenGetWeather_thenWeatherControllerReturnsValidResponse() {

		final String city = "Melbourne";
		final String country = "AU";
		final String description = "clear sky";

		Mockito.when(weatherService.getWeather(city, country)).thenReturn(description);
		final String descriptionResponse = weatherController.getWeather(city, country);

		Assertions.assertEquals(description, descriptionResponse);
	}
}
