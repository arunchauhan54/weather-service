package com.vanguard.weatherservice.controller;

import com.vanguard.weatherservice.service.WeatherService;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherController {

	private final WeatherService weatherService;

	public WeatherController(final WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	public String getWeather(final String city, final String country) {
		return weatherService.getWeather(city, country);
	}
}
