package com.vanguard.weatherservice.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class WeatherResponse {
	private List<Weather> weather;
}