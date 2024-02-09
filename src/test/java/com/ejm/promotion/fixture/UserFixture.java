package com.ejm.promotion.fixture;

import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;

public class UserFixture {

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
}
