package com.ejm.promotion.domain.user.repository;

import java.util.Optional;

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

	public Optional<User> findById(Long userId) {
		return Optional.ofNullable(entityManager.find(User.class, userId));
	}
}
