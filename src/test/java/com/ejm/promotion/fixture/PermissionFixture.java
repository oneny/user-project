package com.ejm.promotion.fixture;

import static com.ejm.promotion.fixture.UserFixture.*;

import java.util.Arrays;
import java.util.List;

import com.ejm.promotion.domain.permisson.dto.PermissionUserDto;
import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.permisson.entity.PermissionType;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;

public class PermissionFixture {

	public static List<Permission> DEFAULT_PERMISSION_LIST = Arrays.stream(PermissionType.values())
		.map(permissionType -> Permission.builder()
			.permissionType(permissionType)
			.build())
		.toList();

	public static Permission DEFAULT_PERMISSION = Permission.builder()
		.permissionType(PermissionType.INSERT)
		.build();

	public static PermissionUserDto INVALID_USER_PERMISSION_REQUEST = PermissionUserDto.builder()
		.build();

	public static PermissionUserDto DEFAULT_PERMISSION_USER_REQUEST = PermissionUserDto.builder()
		.userId(1L)
		.build();

	public static UserPermission DEFAULT_USER_PERMISSION = UserPermission.builder()
		.user(DEFAULT_USER)
		.permission(DEFAULT_PERMISSION)
		.build();
}
