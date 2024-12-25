package com.example.app.service;

import com.example.app.domain.Admin;

public interface AdminService {
	boolean isCorrectIdAndPassword(String loginId, String loginPass)
			throws Exception;

	Admin getAdminByLoginId(String loginId) throws Exception;
}
