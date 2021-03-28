package com.vanguard.weatherservice.dao.repository;

import com.vanguard.weatherservice.dao.entity.WeatherEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WeatherRepositoryTest {

	@Autowired
	private WeatherRepository weatherRepository;


	@Test
	void whenSaved_thenFindsByName() {
		final String city = "Melbourne";
		final String country = "AU";
		final String description = "clear sky";
		final String location = city + "," + country;

		final WeatherEntity weatherEntity = new WeatherEntity();
		weatherEntity.setSearchKey(location);
		weatherEntity.setDescription(description);

		weatherRepository.save(weatherEntity);

		Assertions.assertNotNull(weatherRepository.findById(location));
	}
}
