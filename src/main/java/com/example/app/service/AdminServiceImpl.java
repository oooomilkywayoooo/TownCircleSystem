package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.AdminDao;
import com.example.app.domain.Admin;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminDao dao;

	@Override
	public boolean isCorrectIdAndPassword(String loginId, String loginPass) throws Exception {
		Admin admin = dao.selectByLoginId(loginId);

		// ログインIDが正しいかチェック
		// ログインIDが正しくなければ管理者データは取得されない
		if (admin == null) {
			return false;
		}

		// パスワードが正しいかチェック
		if (!BCrypt.checkpw(loginPass, admin.getLoginPass())) {
			return false;
		}
		return true;
	}

	@Override
	public Admin getAdminByLoginId(String loginId) throws Exception {
		Admin admin = dao.selectByLoginId(loginId);
		return admin;
	}

}
