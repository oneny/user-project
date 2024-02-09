package com.ejm.promotion.domain.userpermission.repository;

import org.springframework.stereotype.Repository;

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
}
