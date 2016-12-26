package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserMemberGroup;
import com.jspxcms.core.domain.UserMemberGroup.UserMemberGroupId;

public interface UserMemberGroupDao extends Repository<UserMemberGroup, UserMemberGroupId> {
	public UserMemberGroup findOne(UserMemberGroupId id);

	// --------------------

	@Modifying
	@Query("delete from UserMemberGroup bean where bean.user.id=?1")
	public int deleteByUserId(Integer userId);

	@Modifying
	@Query("delete from UserMemberGroup bean where bean.group.id=?1")
	public int deleteByGroupId(Integer groupId);
}
