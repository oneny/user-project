package com.ejm.promotion.domain.userpermission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.user.entity.User;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPermissionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void insertUserPermission(UserPermission userPermission) {
		entityManager.persist(userPermission);
	}

	public Optional<UserPermission> findByUserIdAndPermissionId(User user, Permission permission) {
		List<UserPermission> resultList = entityManager.createQuery(
				"select ur from UserPermission ur where ur.user = :user and ur.permission = :permission",
				UserPermission.class)
			.setParameter("user", user)
			.setParameter("permission", permission)
			.getResultList();

		return resultList.stream().findAny();
	}

	public void delete(UserPermission userPermission) {
		entityManager.remove(userPermission);
	}
}
