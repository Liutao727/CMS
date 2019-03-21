package com.jspxcms.common.web;

/**
 * 路径获取接口
 *
 * @author liufang
 */
public interface PathResolver {
    String getPath(String uri);

    String getPath(String uri, String prefix);
}
