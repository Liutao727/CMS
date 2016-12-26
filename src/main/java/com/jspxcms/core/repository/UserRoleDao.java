package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserRole;
import com.jspxcms.core.domain.UserRole.UserRoleId;

public interface UserRoleDao extends Repository<UserRole, UserRoleId> {
	public UserRole findOne(UserRoleId id);

	// --------------------

	@Modifying
	@Query("delete from UserRole bean where bean.user.id=?1")
	public int deleteByUserId(Integer userId);

	@Modifying
	@Query("delete from UserRole bean where bean.role.id=?1")
	public int deleteByRoleId(Integer roleId);
}
