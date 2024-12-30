package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.MemberDao;
import com.example.app.domain.Member;

@Service
public class MemberServiceImpl extends GenericService<Member> implements MemberService {
	@Autowired
	MemberDao memberDao;
	
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
	public void addMember(Member member) throws Exception {
		memberDao.insert(member);
	}

	@Override
	public void editMember(Member member) throws Exception {
		memberDao.update(member);
	}

	@Override
	public void deleteMember(Member member) throws Exception {
		memberDao.delete(member);
	}

}
