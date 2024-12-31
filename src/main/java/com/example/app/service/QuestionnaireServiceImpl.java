package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.QuestionnaireDao;
import com.example.app.domain.Questionnaire;

@Service
public class QuestionnaireServiceImpl extends GenericService<Questionnaire> implements QuestionnaireService {
	
	@Autowired
	QuestionnaireDao questionnaireDao;
	
	public QuestionnaireServiceImpl(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}
	
	@Override
	protected GenericDao<Questionnaire> getDao() {
		return questionnaireDao;
	}
	
	@Override
	public List<Questionnaire> getQuestionnaireList() throws Exception {
		return questionnaireDao.selectAll();
	}

	@Override
	public Questionnaire getQuestionnaireById(Integer id) throws Exception {
		return questionnaireDao.selectById(id);
	}

	@Override
	public void addQuestionnaire(Questionnaire questionnaire) throws Exception {
		questionnaireDao.insert(questionnaire);
	}

	@Override
	public void deleteQuestionnaire(Questionnaire questionnaire) throws Exception {
		questionnaireDao.delete(questionnaire);
	}

}
