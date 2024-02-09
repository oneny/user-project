package com.ejm.promotion.domain.user.exception;

public class NotFoundUserException extends RuntimeException {

	public NotFoundUserException(String message) {
		super(message);
	}
}
