package com.vanguard.weatherservice.dao.repository;


import com.vanguard.weatherservice.dao.entity.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherEntity, String> {
}
