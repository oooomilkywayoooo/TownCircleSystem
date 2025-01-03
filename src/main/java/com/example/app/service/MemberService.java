package com.example.app.service;

import java.util.List;

import com.example.app.domain.Member;
import com.example.app.domain.MemberForm;

public interface MemberService {
	public List<Member>getMemberList() throws Exception;
	public Member getMemberById(Integer id) throws Exception;
	public List<Member> getMemberByGroupId(Integer id) throws Exception;
	public void addMember(MemberForm form) throws Exception;
	public void editMember(Member member) throws Exception;
	public void editPass(Member member) throws Exception;
	public void editEmail(Member member) throws Exception;
	public void deleteMember(Member member) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<Member> getListByPage(int page, int numPerPage) throws Exception;
	// ログイン機能
	public boolean isCorrectEmailAndPassword(String email, String password) throws Exception;
	public Member getMemberByEmail(String email) throws Exception;
}
