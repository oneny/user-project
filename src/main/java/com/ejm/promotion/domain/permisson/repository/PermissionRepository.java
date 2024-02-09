package com.ejm.promotion.domain.permisson.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ejm.promotion.domain.permisson.entity.Permission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PermissionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Permission> findAll() {
		return entityManager.createQuery("select p from Permission p", Permission.class)
			.getResultList();
	}

	public Optional<Permission> findById(Long permissionId) {
		return Optional.ofNullable(entityManager.find(Permission.class, permissionId));
	}
}
