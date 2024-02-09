package com.ejm.promotion.domain.permisson.controller;

import static com.ejm.promotion.global.common.ApiResponse.Status.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejm.promotion.domain.permisson.dto.PermissionUserDto;
import com.ejm.promotion.domain.permisson.service.PermissionService;
import com.ejm.promotion.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionService permissionService;

	@PostMapping("/{permissionId}")
	public ResponseEntity<ApiResponse<Void>> enrollPermission(
		@PathVariable Long permissionId,
		@RequestBody PermissionUserDto permissionUserDto) {

		permissionService.insertUserPermission(permissionId, permissionUserDto);

		return ResponseEntity.ok(ApiResponse.<Void>builder()
			.status(SUCCESS)
			.build());
	}

	@DeleteMapping("/{permissionId}")
	public ResponseEntity<ApiResponse<Void>> denyPermission(
		@PathVariable Long permissionId,
		@RequestBody PermissionUserDto permissionUserDto) {

		permissionService.denyUserPermission(permissionId, permissionUserDto);

		return ResponseEntity.ok(ApiResponse.<Void>builder()
			.status(SUCCESS)
			.build());
	}
}
