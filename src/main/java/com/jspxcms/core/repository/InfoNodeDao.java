package com.jspxcms.core.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.InfoNode;
import com.jspxcms.core.domain.InfoNode.InfoNodeId;

public interface InfoNodeDao extends Repository<InfoNode, InfoNodeId> {
	public InfoNode findOne(InfoNodeId id);

	// --------------------

	@Modifying
	@Query("update InfoNode bean set bean.node.id=?2 where bean.node.id in (?1)")
	public int moveByNodeId(Collection<Integer> nodeIds, Integer nodeId);

	@Modifying
	@Query("delete from InfoNode bean where bean.info.id=?1")
	public int deleteByInfoId(Integer infoId);

	@Modifying
	@Query("delete from InfoNode bean where bean.node.id=?1")
	public int deleteByNodeId(Integer nodeId);
}
