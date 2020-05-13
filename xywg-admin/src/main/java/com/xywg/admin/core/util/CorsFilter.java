package com.xywg.admin.core.util;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

	final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With," +
				" X-Requested-By, If-Modified-Since, X-File-Name, X-File-Type, Cache-Control, Origin");
		response.setHeader("Access-Control-Expose-Headers","Authorization");
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {}
	@Override
	public void destroy() {}
}  