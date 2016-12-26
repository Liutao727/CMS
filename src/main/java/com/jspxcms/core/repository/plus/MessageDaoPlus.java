package com.jspxcms.core.repository.plus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.Message;

public interface MessageDaoPlus {

	public Page<Object[]> groupByUserId(Integer userId, boolean unread, Pageable pageable);

	public Page<Message> findByContactId(Integer userId, Integer contactId, Pageable pageable);

	// public Object[] findTopByUserId(Integer targetId);
}
