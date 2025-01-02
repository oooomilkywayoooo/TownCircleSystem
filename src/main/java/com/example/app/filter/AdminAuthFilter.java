package com.example.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminAuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String path = req.getRequestURI();
		
		// System.out.println(path);
		// 静的リソースや特定のパスをスキップ
		if (path.equals("/admin/login") || path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js")) {
			chain.doFilter(request, response); // 何もせず次のフィルターに進む
			return;
		}

		if(session.getAttribute("adminLoginId") == null) {
			res.sendRedirect("/admin/login");
			return;
		}

		chain.doFilter(request, response);
	}

}
