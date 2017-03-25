package com.jspxcms.core;

import java.io.File;

import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.owasp.html.examples.EbayPolicyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.provider.PersistenceProvider;

import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.factory.HttpComponent4Factory;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.jspxcms.common.image.ImageHandler;
import com.jspxcms.common.image.ImageMagickHandler;
import com.jspxcms.common.image.ImageScalrHandler;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.support.SiteResolver;
import com.querydsl.jpa.EclipseLinkTemplates;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.OpenJPATemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class AppConfig {
	/**
	 * HTML过滤器。防止跨站攻击。
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public PolicyFactory policyFactory() {
		// PolicyFactory policyFactory =
		// Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.STYLES)
		// .and(Sanitizers.LINKS).and(Sanitizers.TABLES).and(Sanitizers.IMAGES);
		return EbayPolicyExample.POLICY_DEFINITION;
	}

	@Value("#{properties['weixin.appid']}")
	private String weixinAppid;
	@Value("#{properties['weixin.secret']}")
	private String weixinSecret;

	/**
	 * 微信公众号TokenHolder
	 * 
	 * @return
	 */
	@Bean
	public TokenHolder tokenHoder() {
		if (StringUtils.isBlank(weixinAppid) || StringUtils.isBlank(weixinSecret)) {
			return null;
		}
		HttpClientFactory.setDefaultFactory(new HttpComponent4Factory());
		WeixinTokenCreator wtc = new WeixinTokenCreator(weixinAppid, weixinSecret);
		File tempDir = FileUtils.getTempDirectory();
		File weixinDir = new File(tempDir, "weixin");
		weixinDir.mkdirs();
		TokenStorager ts = new FileTokenStorager(weixinDir.getAbsolutePath());
		TokenHolder th = new TokenHolder(wtc, ts);
		return th;
	}

	/**
	 * 图片处理器
	 * 
	 * @return
	 */
	@Bean
	public ImageHandler getImageHandler() {
		if (Constants.isGraphicsMagick() || Constants.isImageMagick()) {
			ImageMagickHandler imageMagickHandler = new ImageMagickHandler(Constants.isGraphicsMagick());
			if (StringUtils.isNotBlank(Constants.IM4JAVA_TOOLPATH)) {
				imageMagickHandler.setSearchPath(Constants.IM4JAVA_TOOLPATH);
			}
			return imageMagickHandler;
		} else {
			return new ImageScalrHandler();
		}
	}

	/**
	 * JPA查询Factory
	 * 
	 * @return
	 */
	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		PersistenceProvider provider = PersistenceProvider.fromEntityManager(entityManager);
		switch (provider) {
		case ECLIPSELINK:
			return new JPAQueryFactory(EclipseLinkTemplates.DEFAULT, entityManager);
		case HIBERNATE:
			return new JPAQueryFactory(HQLTemplates.DEFAULT, entityManager);
		case OPEN_JPA:
			return new JPAQueryFactory(OpenJPATemplates.DEFAULT, entityManager);
		case GENERIC_JPA:
		default:
			return new JPAQueryFactory(entityManager);
		}
	}

	@Autowired
	private EntityManager entityManager;

	@Bean
	public SiteResolver foreSiteResolver() {
		return new SiteResolver();
	}

	// @Bean
	// public IPSeeker ipSeeker() throws IOException {
	// Resource resource = resourceLoader.getResource("classpath:qqwry.dat");
	// return new IPSeeker(resource.getFile());
	// }
	// @Autowired
	// private ResourceLoader resourceLoader;
}
