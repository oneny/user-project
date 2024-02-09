package com.ejm.promotion.domain.user.service;

import static com.ejm.promotion.fixture.PermissionFixture.*;
import static com.ejm.promotion.fixture.UserFixture.*;
import static org.mockito.BDDMockito.*;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ejm.promotion.domain.permisson.repository.PermissionRepository;
import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;
import com.ejm.promotion.domain.user.repository.UserRepository;
import com.ejm.promotion.domain.userpermission.repository.UserPermissionRepository;
import com.ejm.promotion.fixture.PermissionFixture;
import com.ejm.promotion.fixture.UserFixture;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	UserService userService;
	@Mock
	UserRepository userRepository;
	@Mock
	PermissionRepository permissionRepository;
	@Mock
	UserPermissionRepository userPermissionRepository;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, permissionRepository, userPermissionRepository);
	}

	@ParameterizedTest(name = "담당자 등록 시 유효성 검사")
	@MethodSource("provideInvalidUserRegistrationRequest")
	void validateUserRegistrationRequest(UserRegistrationDto userRegistrationDto) {
		Assertions.assertThatThrownBy(() -> userService.insertUser(userRegistrationDto))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest(name = "담당자 등록 성공 테스트")
	@MethodSource("provideUserRegistrationRequest")
	void insertUser(UserRegistrationDto userRegistrationDto, int size) {
		given(permissionRepository.findAll())
			.willReturn(DEFAULT_PERMISSION_LIST);

		userService.insertUser(userRegistrationDto);

		then(userPermissionRepository)
			.should(times(size))
			.insertUserPermission(any());
		then(userRepository)
			.should()
			.insertUser(any());
	}


	private static Stream<Arguments> provideInvalidUserRegistrationRequest() {
		return Stream.of(
			Arguments.arguments(INVALID_TYPE_USER_REGISTRATION),
			Arguments.arguments(INVALID_NAME_USER_REGISTRATION)
		);
	}

	private static Stream<Arguments> provideUserRegistrationRequest() {
		return Stream.of(
			Arguments.arguments(DEFAULT_LEADER_USER_REGISTRATION, 4),
			Arguments.arguments(DEFAULT_MANAGER_USER_REGISTRATION, 2)
		);
	}
}