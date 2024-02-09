package com.ejm.promotion.domain.permisson.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ejm.promotion.domain.permisson.dto.PermissionUserDto;
import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.permisson.exception.NotFoundPermissionException;
import com.ejm.promotion.domain.permisson.repository.PermissionRepository;
import com.ejm.promotion.domain.user.entity.User;
import com.ejm.promotion.domain.user.exception.NotFoundUserException;
import com.ejm.promotion.domain.user.repository.UserRepository;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;
import com.ejm.promotion.domain.userpermission.exception.AlreadyGrantPermissionException;
import com.ejm.promotion.domain.userpermission.exception.NotFoundUserPermissionException;
import com.ejm.promotion.domain.userpermission.repository.UserPermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {

	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final UserPermissionRepository userPermissionRepository;

	@Transactional
	public void insertUserPermission(Long permissionId, PermissionUserDto permissionUserDto) {
		validatePermissionUserDto(permissionUserDto);

		User user = userRepository.findById(permissionUserDto.getUserId())
			.orElseThrow(() -> new NotFoundUserException("유저가 없습니다."));
		Permission permission = permissionRepository.findById(permissionId)
			.orElseThrow(() -> new NotFoundPermissionException("권한이 없습니다."));

		userPermissionRepository.findByUserIdAndPermissionId(user, permission)
			.ifPresent(userPermission -> {
				throw new AlreadyGrantPermissionException("이미 권한을 부여했습니다.");
			});

		userPermissionRepository.insertUserPermission(toEntity(user, permission));
	}

	@Transactional
	public void denyUserPermission(Long permissionId, PermissionUserDto permissionUserDto) {
		validatePermissionUserDto(permissionUserDto);

		User user = userRepository.findById(permissionUserDto.getUserId())
			.orElseThrow(() -> new NotFoundUserException("유저가 없습니다."));
		Permission permission = permissionRepository.findById(permissionId)
			.orElseThrow(() -> new NotFoundPermissionException("권한이 없습니다."));
		UserPermission userPermission = userPermissionRepository.findByUserIdAndPermissionId(user, permission)
			.orElseThrow(() -> new NotFoundUserPermissionException("권한을 가지고 있지 않습니다."));

		userPermissionRepository.delete(userPermission);
	}

	private void validatePermissionUserDto(PermissionUserDto permissionUserDto) {
		Assert.notNull(permissionUserDto.getUserId(), "유저 id를 입력하세요.");
	}

	private UserPermission toEntity(User user, Permission permission) {
		return UserPermission.builder()
			.user(user)
			.permission(permission)
			.build();
	}
}
