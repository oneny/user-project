package com.ejm.promotion.domain.user.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;
import com.ejm.promotion.domain.user.entity.UserType;
import com.ejm.promotion.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void insertUser(UserRegistrationDto userRegistrationDto) {
		validateUserRegistrationDto(userRegistrationDto);

		userRepository.insertUser(userRegistrationDto.toEntity());
	}



	private void validateUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
		Assert.hasText(userRegistrationDto.getName(), "이름을 입력하세요.");

		if (Arrays.stream(UserType.values())
			.noneMatch(userType -> userType.name().equals(userRegistrationDto.getUserType()))) {
			throw new IllegalArgumentException("%s에 해당하는 유저 타입이 없습니다.".formatted(userRegistrationDto.getUserType()));
		}
	}
}
