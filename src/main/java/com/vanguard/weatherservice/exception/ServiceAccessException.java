package com.vanguard.weatherservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceAccessException extends RuntimeException {

	private final String message;
	private final HttpStatus status;

	public ServiceAccessException(final String message, final HttpStatus status) {
		this.message = message;
		this.status = status;
	}
}
