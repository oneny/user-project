package com.ejm.promotion.global.advice;

import static com.ejm.promotion.global.common.ApiResponse.Status.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ejm.promotion.global.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ApiResponse<Void>> illegalArgumentException(IllegalArgumentException exception) {
		log.info("IllegalArgumentException: {}", exception.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.<Void>builder()
				.status(FAIL)
				.message(exception.getMessage())
				.build());
	}
}
