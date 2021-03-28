package com.vanguard.weatherservice.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WeatherEntity {

	@Id
	private String searchKey;
	private String description;
}
