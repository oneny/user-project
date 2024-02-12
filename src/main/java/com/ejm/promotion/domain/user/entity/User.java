package com.ejm.promotion.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.ejm.promotion.domain.permisson.entity.Permission;
import com.ejm.promotion.domain.userpermission.entity.UserPermission;
import com.ejm.promotion.global.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserPermission> userPermissionList = new ArrayList<>();

	public boolean compareUserType(UserType userType) {
		return this.userType.equals(userType);
	}

	public List<UserPermission> addDefaultPermissions(List<Permission> permissionList) {
		return permissionList.stream()
			.filter(pl -> this.compareUserType(UserType.LEADER) || pl.isDefaultManagerPermission())
			.map(permission -> {
				UserPermission userPermission = UserPermission.builder()
					.user(this)
					.permission(permission)
					.build();
				this.getUserPermissionList().add(userPermission);

				return userPermission;
			})
			.toList();
	}
}
