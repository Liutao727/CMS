package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.InfoTag;
import com.jspxcms.core.domain.InfoTag.InfoTagId;

public interface InfoTagDao extends Repository<InfoTag, InfoTagId> {
	public InfoTag findOne(InfoTagId id);

	// --------------------

	@Modifying
	@Query("delete from InfoTag t where t.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoTag t where t.tag.id=?1")
	public int deleteByTagId(Integer tagId);
}
