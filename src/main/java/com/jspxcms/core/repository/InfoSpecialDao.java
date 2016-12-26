package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.InfoSpecial;
import com.jspxcms.core.domain.InfoSpecial.InfoSpecialId;

public interface InfoSpecialDao extends Repository<InfoSpecial, InfoSpecialId> {
	public InfoSpecial findOne(InfoSpecialId id);

	// --------------------

	@Modifying
	@Query("delete from InfoSpecial bean where bean.special.id=?1")
	public int deleteBySpecialId(Integer specialId);
}
