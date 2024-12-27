package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Member;

@Mapper
public interface MemberDao extends GenericDao<Member> {
	public List<Member> selectAll() throws Exception;
	public Member selectById(Integer id) throws Exception;
	public List<Member> selectByGroupId(Integer id) throws Exception;
	public void insert(Member member) throws Exception;
	public void update(Member member) throws Exception;
	public void delete(Member member) throws Exception;
}
