package com.ejm.promotion.domain.user.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.permisson.exception.NotFoundPermissionException;
import com.ejm.promotion.domain.permisson.repository.PermissionRepository;
import com.ejm.promotion.domain.user.dto.request.UserPermissionDto;
import com.ejm.promotion.domain.user.dto.request.UserRegistrationDto;
import com.ejm.promotion.domain.user.entity.User;
import com.ejm.promotion.domain.user.entity.UserType;
import com.ejm.promotion.domain.user.exception.NotFoundUserException;
import com.ejm.promotion.domain.user.repository.UserRepository;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;
import com.ejm.promotion.domain.userpermission.exception.NotFoundUserPermissionException;
import com.ejm.promotion.domain.userpermission.repository.UserPermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final UserPermissionRepository userPermissionRepository;

	@Transactional
	public void insertUser(UserRegistrationDto userRegistrationDto) {
		validateUserRegistrationDto(userRegistrationDto);
		User user = userRegistrationDto.toEntity();

		List<Permission> permissionList = permissionRepository.findAll();
		List<UserPermission> userLeaderPermissions = user.addDefaultPermissions(permissionList);
		userLeaderPermissions.forEach(userPermissionRepository::insertUserPermission);

		userRepository.insertUser(user);
	}

	@Transactional(readOnly = true)
	public void checkPermission(Long userId, UserPermissionDto userPermissionDto) {
		validateUserPermissionDto(userPermissionDto);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundUserException("유저가 없습니다."));
		Permission permission = permissionRepository.findById(userPermissionDto.getPermissionId())
			.orElseThrow(() -> new NotFoundPermissionException("권한이 없습니다."));

		userPermissionRepository.findByUserIdAndPermissionId(user, permission)
			.orElseThrow(() -> new NotFoundUserPermissionException("권한을 가지고 있지 않습니다."));
	}

	private void validateUserPermissionDto(UserPermissionDto userPermissionDto) {
		Assert.notNull(userPermissionDto.getPermissionId(), "권한 id를 입력하세요.");
	}

	private void validateUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
		Assert.hasText(userRegistrationDto.getName(), "이름을 입력하세요.");

		if (Arrays.stream(UserType.values())
			.noneMatch(userType -> userType.name().equals(userRegistrationDto.getUserType()))) {
			throw new IllegalArgumentException("%s에 해당하는 유저 타입이 없습니다.".formatted(userRegistrationDto.getUserType()));
		}
	}
}
