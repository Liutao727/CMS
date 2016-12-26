package com.jspxcms.core.service;

import com.jspxcms.core.domain.Info;

public interface InfoSpecialService {
	public void update(Info info, Integer[] specialIds);

	public int deleteBySpecialId(Integer specialId);
}
