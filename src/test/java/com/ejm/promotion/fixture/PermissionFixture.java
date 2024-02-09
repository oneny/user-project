package com.ejm.promotion.fixture;

import java.util.Arrays;
import java.util.List;

import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.permisson.entity.PermissionType;

public class PermissionFixture {

	public static List<Permission> DEFAULT_PERMISSION_LIST = Arrays.stream(PermissionType.values())
		.map(permissionType -> Permission.builder()
			.permissionType(permissionType)
			.build())
		.toList();

	public static Permission DEFAULT_PERMISSION = Permission.builder()
		.permissionType(PermissionType.INSERT)
		.build();
}
