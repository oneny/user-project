package com.ejm.promotion.fixture;

import com.ejm.promotion.domain.user.dto.request.UserPermissionDto;
import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;
import com.ejm.promotion.domain.user.entity.User;
import com.ejm.promotion.domain.user.entity.UserType;

public class UserFixture {

	public static User DEFAULT_USER = User.builder()
		.id(1L)
		.name("oneny")
		.userType(UserType.LEADER)
		.build();

	public static UserRegistrationDto DEFAULT_MANAGER_USER_REGISTRATION = UserRegistrationDto.builder()
		.name("oneny")
		.userType("MANAGER")
		.build();

	public static UserRegistrationDto DEFAULT_LEADER_USER_REGISTRATION = UserRegistrationDto.builder()
		.name("oneny")
		.userType("LEADER")
		.build();

	public static UserRegistrationDto INVALID_NAME_USER_REGISTRATION = UserRegistrationDto.builder()
		.name("")
		.userType("MANAGER")
		.build();

	public static UserRegistrationDto INVALID_TYPE_USER_REGISTRATION = UserRegistrationDto.builder()
		.name("oneny")
		.userType("USER")
		.build();

	public static UserPermissionDto INVALID_USER_PERMISSION = UserPermissionDto.builder()
		.build();

	public static UserPermissionDto DEFAULT_USER_PERMISSION_REQUEST = UserPermissionDto.builder()
		.permissionId(1L)
		.build();
}
