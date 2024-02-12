package com.ejm.promotion.domain.user.controller;

import static com.ejm.promotion.global.common.ApiResponse.Status.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejm.promotion.domain.user.dto.request.UserPermissionDto;
import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;
import com.ejm.promotion.domain.user.dto.response.PermissionResponseDto;
import com.ejm.promotion.domain.user.service.UserService;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;
import com.ejm.promotion.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
		userService.insertUser(userRegistrationDto);

		return ResponseEntity.ok(ApiResponse.<Void>builder()
			.status(SUCCESS)
			.build());
	}

	@PostMapping("/{userId}/permission")
	public ResponseEntity<ApiResponse<PermissionResponseDto>> checkPermission(
		@PathVariable Long userId,
		@RequestBody UserPermissionDto userPermissionDto
	) {

		return ResponseEntity.ok(ApiResponse.<PermissionResponseDto>builder()
			.status(SUCCESS)
			.result(userService.checkPermission(userId, userPermissionDto))
			.build());
	}
}
