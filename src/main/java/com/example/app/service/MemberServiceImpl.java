package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.MemberDao;
import com.example.app.domain.Group;
import com.example.app.domain.Member;
import com.example.app.domain.MemberForm;

@Service
public class MemberServiceImpl extends GenericService<Member> implements MemberService {
	@Autowired
	MemberDao memberDao;
	@Autowired
	GroupService groupService;

	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	protected GenericDao<Member> getDao() {
		return memberDao;
	}

	@Override
	public List<Member> getMemberList() throws Exception {
		return memberDao.selectAll();
	}

	@Override
	public Member getMemberById(Integer id) throws Exception {
		return memberDao.selectById(id);
	}

	@Override
	public List<Member> getMemberByGroupId(Integer id) throws Exception {
		return memberDao.selectByGroupId(id);
	}

	@Override
	public void addMember(MemberForm form) throws Exception {
		// グループ情報を取得
		Group group = groupService.getGroupById(form.getGroupId());
		// フォームデータをMemberクラスに変換
		Member member = new Member();
		member.setName(form.getLastName() + form.getFirstName());
		member.setNameKana(form.getLastNameKana() + form.getFirstNameKana());
		member.setAddress(form.getAddress());
		member.setTel(form.getTel());
		member.setEmail(form.getEmail());
		member.setFamilyNumber(form.getFamilyNumber());
		member.setPassword(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
		member.setGroup(group);
		// DBに保存
		memberDao.insert(member);
	}

	@Override
	public void editMember(Member member) throws Exception {
		memberDao.update(member);
	}
	
	@Override
	public void editPass(Member member) throws Exception {
		memberDao.updatePass(member);
	}

	@Override
	public void editEmail(Member member) throws Exception {
		memberDao.updateEmail(member);
	}

	@Override
	public void deleteMember(Member member) throws Exception {
		memberDao.delete(member);
	}
	
	// ログイン機能
	@Override
	public boolean isCorrectEmailAndPassword(String email, String password) throws Exception {
		Member member = memberDao.selectByEmail(email);
		
		// ログインEmailが正しいかチェック
		// ログインEmailが正しくなければ会員データは取得されない
		if (member == null) {
			return false;
		}

		// パスワードが正しいかチェック
		if (!BCrypt.checkpw(password, member.getPassword())) {
			return false;
		}
		return true;
	}

	@Override
	public Member getMemberByEmail(String email) throws Exception {
		Member member = memberDao.selectByEmail(email);
		return member;
	}

}
