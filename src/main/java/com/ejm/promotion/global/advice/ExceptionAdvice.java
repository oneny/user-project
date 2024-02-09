package com.ejm.promotion.global.advice;

import static com.ejm.promotion.global.common.ApiResponse.Status.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ejm.promotion.domain.permisson.exception.NotFoundPermissionException;
import com.ejm.promotion.domain.user.exception.NotFoundUserException;
import com.ejm.promotion.domain.userpermission.exception.AlreadyGrantPermissionException;
import com.ejm.promotion.domain.userpermission.exception.NotFoundUserPermissionException;
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

	@ExceptionHandler(NotFoundUserException.class)
	protected ResponseEntity<ApiResponse<Void>> notFoundUserException(NotFoundUserException exception) {
		log.info("NotFoundUserException: {}", exception.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.<Void>builder()
				.status(FAIL)
				.message(exception.getMessage())
				.build());
	}

	@ExceptionHandler(NotFoundPermissionException.class)
	protected ResponseEntity<ApiResponse<Void>> notFoundPermissionException(NotFoundPermissionException exception) {
		log.info("NotFoundPermissionException: {}", exception.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.<Void>builder()
				.status(FAIL)
				.message(exception.getMessage())
				.build());
	}

	@ExceptionHandler(NotFoundUserPermissionException.class)
	protected ResponseEntity<ApiResponse<Void>> notFoundUserPermissionException(NotFoundUserPermissionException exception) {
		log.info("NotFoundUserPermissionException: {}", exception.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.<Void>builder()
				.status(FAIL)
				.message(exception.getMessage())
				.build());
	}

	@ExceptionHandler(AlreadyGrantPermissionException.class)
	protected ResponseEntity<ApiResponse<Void>> alreadyGrantPermissionException(AlreadyGrantPermissionException exception) {
		log.info("AlreadyGrantPermissionException: {}", exception.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.<Void>builder()
				.status(FAIL)
				.message(exception.getMessage())
				.build());
	}
}
