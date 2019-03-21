package com.jspxcms.core.support;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class CsrfFilter extends OncePerRequestFilter {
    private Collection<String> domains = new HashSet<>();

    public CsrfFilter(Collection<String> domains) {
        this.domains = domains;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // GET 等方式不用提供Token，自动放行，不能用于修改数据。修改数据必须使用 POST、PUT、DELETE、PATCH 方式并且Referer要合法。
        if (Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS").contains(request.getMethod()) || domains.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        // 从 HTTP 头中取得 Referer 值
        String referer = request.getHeader("Referer");
        // 判断 Referer 是否以 合法的域名 开头。
        if (referer != null) {
            // 如 http://mysite.com/abc.html https://www.mysite.com:8080/abc.html
            if (referer.indexOf("://") > 0) {
                referer.substring(referer.indexOf("://") + 3);
            }
            // 如 mysite.com/abc.html
            if (referer.indexOf("/") > 0) {
                referer.substring(0, referer.indexOf("/"));
            }
            // 如 mysite.com:8080
            if (referer.indexOf(":") > 0) {
                referer.substring(0, referer.indexOf(":"));
            }
            // 如 mysite.com
            for (String domain : domains) {
                if (referer.endsWith(domain)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Protection: Referer Illegal");
    }
}
