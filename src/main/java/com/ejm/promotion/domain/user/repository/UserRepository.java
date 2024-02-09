package com.ejm.promotion.domain.user.repository;

import org.springframework.stereotype.Repository;

import com.ejm.promotion.domain.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	@PersistenceContext
	private final EntityManager entityManager;

	public void insertUser(User user) {
		entityManager.persist(user);
	}
}
