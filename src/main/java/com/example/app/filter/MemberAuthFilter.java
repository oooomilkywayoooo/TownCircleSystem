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

public class MemberAuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String path = req.getRequestURI();
		
		// System.out.println(path);
		// 管理者・ログイン画面・ログアウト画面をスキップする
		if (path.startsWith("/admin/")|| path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".ico")
				|| path.startsWith("/images/") || path.startsWith("/uploads/") || path.startsWith("/signinTemplate")) {
            chain.doFilter(request, response); // 何もせず次のフィルターに進む
            return;
        }
		
		if (path.equals("/login") || path.equals("/logout")) {
            chain.doFilter(request, response); // 何もせず次のフィルターに進む
            return;
        }
		
		if(session.getAttribute("memberId") == null) {
			res.sendRedirect("/login");
			return;
		}

		chain.doFilter(request, response);
	}

}
