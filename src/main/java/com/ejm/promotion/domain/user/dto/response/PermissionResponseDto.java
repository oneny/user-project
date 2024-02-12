package com.ejm.promotion.domain.user.dto.response;

import java.time.LocalDateTime;

import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.user.entity.User;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionResponseDto {

	private Long id;
	private User user;
	private Permission permission;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static PermissionResponseDto from(UserPermission userPermission) {
		return PermissionResponseDto.builder()
			.id(userPermission.getId())
			.user(userPermission.getUser())
			.permission(userPermission.getPermission())
			.createdAt(userPermission.getCreatedAt())
			.updatedAt(userPermission.getUpdatedAt())
			.build();
	}
}
