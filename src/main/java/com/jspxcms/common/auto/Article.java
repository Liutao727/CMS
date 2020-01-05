package com.jspxcms.common.auto;


public class Article {
	private Integer id;
	private String title;
	private String content;
	private String image;
	private String time;
	private String editer;
	private Integer count;//浏览次数
	private String type;
	private String notice;
	private Integer count1;//评论次数
	private String keywords;
	private String url;//原文鏈接
	
	public Article() {}

	public Article(String title, String content, String image, String time,
			String editer, Integer count, String type, String notice,
			Integer count1, String keywords,String url) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.time = time;
		this.editer = editer;
		this.count = count;
		this.type = type;
		this.notice = notice;
		this.count1 = count1;
		this.keywords = keywords;
		this.url=url;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEditer() {
		return editer;
	}

	public void setEditer(String editer) {
		this.editer = editer;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Integer getCount1() {
		return count1;
	}

	public void setCount1(Integer count1) {
		this.count1 = count1;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content="
				+ content + ", image=" + image + ", time=" + time + ", editer="
				+ editer + ", count=" + count + ", type=" + type + ", notice="
				+ notice + ", count1=" + count1 + ", keywords=" + keywords+"url="+url
				+ "]";
	}
	
	
}
