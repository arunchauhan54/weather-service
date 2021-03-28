package com.vanguard.weatherservice.handler;

import com.vanguard.weatherservice.exception.ServiceAccessException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceAccessExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(ServiceAccessException.class)
	public final ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
			final ServiceAccessException exception) {

		return ResponseEntity.status(exception.getStatus())
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ApiErrorResponse(exception.getStatus(), exception.getMessage(), exception));
	}


}
