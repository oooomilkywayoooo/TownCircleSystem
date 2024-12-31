package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Questionnaire;

@Mapper
public interface QuestionnaireDao extends GenericDao<Questionnaire> {
	public List<Questionnaire> selectAll() throws Exception;
	public Questionnaire selectById(Integer id) throws Exception;
	public void insert(Questionnaire questionnaire) throws Exception;
	public void delete(Questionnaire questionnaire) throws Exception;
}
