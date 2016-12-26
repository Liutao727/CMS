package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserOrg;
import com.jspxcms.core.domain.UserOrg.UserOrgId;

public interface UserOrgDao extends Repository<UserOrg, UserOrgId> {
	public UserOrg findOne(UserOrgId id);

	// --------------------

	@Modifying
	@Query("delete from UserOrg bean where bean.user.id=?1")
	public int deleteByUserId(Integer userId);

	@Modifying
	@Query("delete from UserOrg bean where bean.org.id=?1")
	public int deleteByOrgId(Integer orgId);
}
