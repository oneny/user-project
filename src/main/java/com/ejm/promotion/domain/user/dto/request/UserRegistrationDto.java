package com.ejm.promotion.domain.user.dto.request;

import com.ejm.promotion.domain.user.entity.User;
import com.ejm.promotion.domain.user.entity.UserType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegistrationDto {

	private String name;
	private String userType;

	public User toEntity() {

		return User.builder()
			.name(name)
			.userType(UserType.valueOf(userType))
			.build();
	}
}
