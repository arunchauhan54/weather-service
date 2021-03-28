package com.vanguard.weatherservice.client;

import javax.validation.constraints.NotEmpty;

import com.vanguard.weatherservice.model.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openWeatherClient", url = "${app.weatherMapUrl}")
@Validated
public interface WeatherClient {

	@RequestMapping(path = "/", produces = "application/json", method = RequestMethod.GET)
	WeatherResponse getWeather(
			@NotEmpty @RequestParam("q") final String location,
			@NotEmpty @RequestParam("appid") final String apiKey);

}
