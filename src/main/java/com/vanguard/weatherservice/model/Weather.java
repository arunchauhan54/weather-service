package com.vanguard.weatherservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class Weather {
	private String description;
}
