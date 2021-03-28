package com.vanguard.weatherservice.handler;

import feign.FeignException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FeignExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(FeignException.class)
	public final ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
			final FeignException exception) {

		return ResponseEntity.status(exception.status())
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ApiErrorResponse(
						HttpStatus.valueOf(exception.status()),
						"error from third party",
						exception));
	}


}
