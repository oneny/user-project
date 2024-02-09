package com.ejm.promotion.global.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

	private Status status;
	private String message;
	private T result;

	public enum Status {
		SUCCESS, FAIL
	}
}
