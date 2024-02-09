package com.ejm.promotion.domain.permisson.service;

import static com.ejm.promotion.fixture.PermissionFixture.*;
import static com.ejm.promotion.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ejm.promotion.domain.permisson.exception.NotFoundPermissionException;
import com.ejm.promotion.domain.permisson.repository.PermissionRepository;
import com.ejm.promotion.domain.user.exception.NotFoundUserException;
import com.ejm.promotion.domain.user.repository.UserRepository;
import com.ejm.promotion.domain.userpermission.exception.AlreadyGrantPermissionException;
import com.ejm.promotion.domain.userpermission.exception.NotFoundUserPermissionException;
import com.ejm.promotion.domain.userpermission.repository.UserPermissionRepository;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

	PermissionService permissionService;
	@Mock
	UserRepository userRepository;
	@Mock
	PermissionRepository permissionRepository;
	@Mock
	UserPermissionRepository userPermissionRepository;

	@BeforeEach
	void setUp() {
		permissionService = new PermissionService(userRepository, permissionRepository, userPermissionRepository);
	}

	@Test
	@DisplayName("권한 부여 실패 테스트: 유효성 검증 실패")
	void validateInsertUserPermission() {
		assertThatThrownBy(() -> permissionService.insertUserPermission(1L, INVALID_USER_PERMISSION_REQUEST))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("권한 부여 실패 테스트: 담당자 없음")
	void noExistUserWithInsertPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> permissionService.insertUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST))
			.isInstanceOf(NotFoundUserException.class);
	}

	@Test
	@DisplayName("권한 부여 실패 테스트: 권한 없음")
	void noExistPermissionWithInsertPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> permissionService.insertUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST))
			.isInstanceOf(NotFoundPermissionException.class);
	}

	@Test
	@DisplayName("권한 부여 실패 테스트: 권한 중복 부여")
	void alreadyExistUserPermissionWithInsertPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_PERMISSION));
		given(userPermissionRepository.findByUserIdAndPermissionId(DEFAULT_USER, DEFAULT_PERMISSION))
			.willReturn(Optional.ofNullable(DEFAULT_USER_PERMISSION));

		assertThatThrownBy(() -> permissionService.insertUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST))
			.isInstanceOf(AlreadyGrantPermissionException.class);
	}

	@Test
	@DisplayName("권한 부여 성공 테스트")
	void insertUserPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_PERMISSION));
		given(userPermissionRepository.findByUserIdAndPermissionId(DEFAULT_USER, DEFAULT_PERMISSION))
			.willReturn(Optional.empty());

		permissionService.insertUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST);

		then(userPermissionRepository)
			.should()
			.insertUserPermission(any());
	}

	@Test
	@DisplayName("권한 삭제 실패 테스트: 유효성 검증 실패")
	void validateDenyUserPermissionWith() {
		assertThatThrownBy(() -> permissionService.insertUserPermission(1L, INVALID_USER_PERMISSION_REQUEST))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("권한 삭제 실패 테스트: 담당자 없음")
	void noExistUserWithDenyPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> permissionService.denyUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST))
			.isInstanceOf(NotFoundUserException.class);
	}

	@Test
	@DisplayName("권한 삭제 실패 테스트: 권한 없음")
	void noExistPermissionWithDenyPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> permissionService.denyUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST))
			.isInstanceOf(NotFoundPermissionException.class);
	}

	@Test
	@DisplayName("권한 삭 실패 테스트: 권한 중복 부여")
	void alreadyExistUserPermissionWithDenyPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_PERMISSION));
		given(userPermissionRepository.findByUserIdAndPermissionId(DEFAULT_USER, DEFAULT_PERMISSION))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> permissionService.denyUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST))
			.isInstanceOf(NotFoundUserPermissionException.class);
	}

	@Test
	@DisplayName("권한 삭제 성공 테스트")
	void denyUserPermission() {
		given(userRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_USER));
		given(permissionRepository.findById(1L))
			.willReturn(Optional.ofNullable(DEFAULT_PERMISSION));
		given(userPermissionRepository.findByUserIdAndPermissionId(DEFAULT_USER, DEFAULT_PERMISSION))
			.willReturn(Optional.ofNullable(DEFAULT_USER_PERMISSION));

		permissionService.denyUserPermission(1L, DEFAULT_PERMISSION_USER_REQUEST);

		then(userPermissionRepository)
			.should()
			.delete(any());
	}
}