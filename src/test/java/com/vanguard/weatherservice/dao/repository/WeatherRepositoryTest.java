package com.vanguard.weatherservice.dao.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vanguard.weatherservice.dao.entity.WeatherEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WeatherRepositoryTest {

	@Autowired
	private WeatherRepository weatherRepository;


	@Test
	@DisplayName("save record and get it from database")
	void givenRecordSaved_whenFindsById_thenResultReturned() {
		final String city = "Melbourne";
		final String country = "AU";
		final String description = "clear sky";
		final String location = city + "," + country;

		final WeatherEntity weatherEntity = new WeatherEntity();
		weatherEntity.setSearchKey(location);
		weatherEntity.setDescription(description);

		weatherRepository.save(weatherEntity);

		final WeatherEntity entity = weatherRepository.findById(location).orElseThrow();

		assertEquals(description, entity.getDescription());
	}
}
