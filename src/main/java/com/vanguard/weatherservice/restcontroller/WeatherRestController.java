package com.vanguard.weatherservice.restcontroller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.constraints.NotEmpty;

import com.vanguard.weatherservice.controller.WeatherController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "weather service")
@RestController
@RequestMapping(value = "/weather", produces = APPLICATION_JSON_VALUE)
@Validated
public class WeatherRestController {

	private final WeatherController weatherController;

	public WeatherRestController(final WeatherController weatherController) {
		this.weatherController = weatherController;
	}

	@GetMapping
	@Operation(summary = "get weather description", description = "returns weather description for a city and country")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "returns description of the weather"),
			@ApiResponse(responseCode = "400", description = "validation failed", content = @Content),
			@ApiResponse(responseCode = "403", description = "forbidden", content = @Content),
			@ApiResponse(responseCode = "429", description = "too many requests", content = @Content)
	})
	@ResponseStatus(HttpStatus.OK)
	public String getWeather(@NotEmpty final String city, @NotEmpty final String country) {
		return weatherController.getWeather(city, country);
	}

}
