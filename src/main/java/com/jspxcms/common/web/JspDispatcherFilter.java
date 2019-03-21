package com.jspxcms.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class JspDispatcherFilter implements Filter {
    /**
     * 是否允许访问 JSP 或 JSPX 文件。默认 false 。
     */
    private boolean allowed = false;
    /**
     * 请求转发地址前缀。只允许特定目录的 jsp(jspx) 允许被访问。默认为 /jsp 。比如访问 /abc.jsp 通过请求转发实际上访问的文件为 /jsp/abc.jsp 。
     */
    private String prefix = "/jsp";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!allowed) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "JSP Access Denied");
            return;
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        if (StringUtils.isNotBlank(ctx)) {
            uri = uri.substring(ctx.length());
        }
        request.getRequestDispatcher(prefix + uri).forward(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String allowed = filterConfig.getInitParameter("allowed");
        if ("true".equals(allowed)) {
            this.allowed = true;
        }
        String prefix = filterConfig.getInitParameter("prefix");
        if (StringUtils.isNotBlank(prefix)) {
            this.prefix = prefix;
        }
    }

    public void destroy() {
    }
}
