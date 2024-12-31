package com.example.app.service;

import java.util.List;

import com.example.app.domain.Questionnaire;

public interface QuestionnaireService {
	public List<Questionnaire>getQuestionnaireList() throws Exception;
	public Questionnaire getQuestionnaireById(Integer id) throws Exception;
	public void addQuestionnaire(Questionnaire questionnaire) throws Exception;
	public void deleteQuestionnaire(Questionnaire questionnaire) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<Questionnaire> getListByPage(int page, int numPerPage) throws Exception;
}
