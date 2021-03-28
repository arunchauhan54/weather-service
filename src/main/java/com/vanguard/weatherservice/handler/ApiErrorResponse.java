package com.vanguard.weatherservice.handler;


import java.time.LocalDateTime;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

	private HttpStatus status;
	private LocalDateTime localDateTime;
	private String message;
	private String detailMessage;

	public ApiErrorResponse(final HttpStatus status, final String message, final Throwable ex) {
		this.status = status;
		this.localDateTime = LocalDateTime.now();
		this.message = message;
		this.detailMessage = ex.getLocalizedMessage();
	}
}
