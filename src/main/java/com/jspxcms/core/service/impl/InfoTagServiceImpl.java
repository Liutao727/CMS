package com.jspxcms.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoTag;
import com.jspxcms.core.domain.InfoTag.InfoTagId;
import com.jspxcms.core.domain.Tag;
import com.jspxcms.core.repository.InfoTagDao;
import com.jspxcms.core.service.InfoTagService;
import com.jspxcms.core.service.TagService;

@Service
@Transactional(readOnly = true)
public class InfoTagServiceImpl implements InfoTagService {
	private InfoTag findOrCreate(Info info, Tag tag) {
		InfoTag bean = dao.findOne(new InfoTagId(info.getId(), tag.getId()));
		if (bean == null) {
			bean = new InfoTag(info, tag);
		}
		return bean;
	}

	@Transactional
	public void update(Info info, String[] tagNames) {
		// 为null不更新。要设置为空，请传空数组。
		if (tagNames == null) {
			return;
		}
		List<InfoTag> infoTags = info.getInfoTags();
		for (InfoTag infoTag : infoTags) {
			infoTag.getTag().derefer();
		}
		infoTags.clear();
		Integer siteId = info.getSite().getId();
		for (String tagName : tagNames) {
			infoTags.add(findOrCreate(info, tagService.refer(tagName, siteId)));
		}
	}

	@Transactional
	public int deleteByInfoId(Integer infoId) {
		return dao.deleteByInfoId(infoId);
	}

	@Transactional
	public int deleteByTagId(Integer tagId) {
		return dao.deleteByTagId(tagId);
	}

	private TagService tagService;

	@Autowired
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	private InfoTagDao dao;

	@Autowired
	public void setDao(InfoTagDao dao) {
		this.dao = dao;
	}
}
