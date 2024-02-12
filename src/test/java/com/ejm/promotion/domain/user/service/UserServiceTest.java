package com.ejm.promotion.domain.user.service;

import static com.ejm.promotion.fixture.PermissionFixture.*;
import static com.ejm.promotion.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ejm.promotion.domain.permisson.exception.NotFoundPermissionException;
import com.ejm.promotion.domain.permisson.repository.PermissionRepository;
import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;
import com.ejm.promotion.domain.user.dto.response.PermissionResponseDto;
import com.ejm.promotion.domain.user.exception.NotFoundUserException;
import com.ejm.promotion.domain.user.repository.UserRepository;
import com.ejm.promotion.domain.userpermission.exception.NotFoundUserPermissionException;
import com.ejm.promotion.domain.userpermission.repository.UserPermissionRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	UserService userService;
	@Mock
	UserRepository userRepository;
	@Mock
	PermissionRepository permissionRepository;
	@Mock
	UserPermissionRepository userPermissionRepository;

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

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, permissionRepository, userPermissionRepository);
	}

	@ParameterizedTest(name = "담당자 등록 시 유효성 검사")
	@MethodSource("provideInvalidUserRegistrationRequest")
	void validateUserRegistrationRequest(UserRegistrationDto userRegistrationDto) {
		assertThatThrownBy(() -> userService.insertUser(userRegistrationDto))
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

	@Test
	@DisplayName("담당자 권한 보유 체크 시 유효성 검증")
	void validateUserPermissionDto() {
		assertThatThrownBy(() -> userService.checkPermission(1L, INVALID_USER_PERMISSION))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("담당자 권한 보유 체크 실패 테스트: 담당자 없음")
	void notExistUserWithCheckPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> userService.checkPermission(1L, DEFAULT_USER_PERMISSION_REQUEST))
			.isInstanceOf(NotFoundUserException.class);
	}

	@Test
	@DisplayName("담당자 권한 보유 체크 실패 테스트: 권한 없음")
	void notExistPermissionWithCheckPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> userService.checkPermission(1L, DEFAULT_USER_PERMISSION_REQUEST))
			.isInstanceOf(NotFoundPermissionException.class);
	}

	@Test
	@DisplayName("담당자 권한 보유 체크 실패 테스트: 유저 권한 없음")
	void notExistUserPermissionWithCheckPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_PERMISSION));
		given(userPermissionRepository.findByUserIdAndPermissionId(DEFAULT_USER, DEFAULT_PERMISSION))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> userService.checkPermission(1L, DEFAULT_USER_PERMISSION_REQUEST))
			.isInstanceOf(NotFoundUserPermissionException.class);
	}

	@Test
	@DisplayName("담당자 권한 보유 체크 성공 테스트")
	void successCheckUserPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_PERMISSION));
		given(userPermissionRepository.findByUserIdAndPermissionId(DEFAULT_USER, DEFAULT_PERMISSION))
			.willReturn(Optional.ofNullable(DEFAULT_USER_PERMISSION));

		PermissionResponseDto permissionResponseDto = userService.checkPermission(1L, DEFAULT_USER_PERMISSION_REQUEST);

		assertAll(
			() -> assertThat(permissionResponseDto.getId()).isEqualTo(1L),
			() -> assertThat(permissionResponseDto.getUser().getId()).isEqualTo(1L),
			() -> assertThat(permissionResponseDto.getPermission().getId()).isEqualTo(1L)
		);
	}
}