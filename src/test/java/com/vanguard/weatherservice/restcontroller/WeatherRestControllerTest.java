package com.vanguard.weatherservice.restcontroller;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.ConstraintViolationException;

import com.vanguard.weatherservice.config.WeatherProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class WeatherRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private WeatherProperties weatherProperties;

	@Test
	@DisplayName("service returns illegalArgumentException for null city value")
	void givenNullCityName_whenCallService_thenServiceReturnsIllegalArgumentException() throws Exception {
		final String key = weatherProperties.getApiKeys().get(0);

		mockMvc.perform(
				get("/weather")
						.contentType(APPLICATION_JSON)
						.header("API_KEY", key)
						.param("country", "AU"))
				.andExpect(status().is4xxClientError())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
	}

	@Test
	@DisplayName("service returns illegalArgumentException for null country value")
	void givenEmptyCountryName_whenCallService_thenServiceReturnsIllegalArgumentException() throws Exception {
		final String key = weatherProperties.getApiKeys().get(0);

		mockMvc.perform(
				get("/weather")
						.contentType(APPLICATION_JSON)
						.header("API_KEY", key)
						.param("city", "Melbourne")
						.param("country", ""))
				.andExpect(status().is4xxClientError())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
	}

	@Test
	@DisplayName("for valid input service returns successful response")
	void givenValidCityAndCountry_whenCallService_thenGetValidResponse() throws Exception {
		final String key = weatherProperties.getApiKeys().get(0);

		mockMvc.perform(
				get("/weather")
						.content(APPLICATION_JSON_VALUE)
						.header("API_KEY", key)
						.param("city", "Melbourne")
						.param("country", "Australia"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(not(emptyOrNullString())));

	}
}
