package com.clinkast.qcm.services.impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinkast.qcm.entities.Proposition;
import com.clinkast.qcm.entities.Question;
import com.clinkast.qcm.entities.Result;
import com.clinkast.qcm.entities.Topic;
import com.clinkast.qcm.repositories.PropositionRepository;
import com.clinkast.qcm.repositories.QuestionRepository;
import com.clinkast.qcm.repositories.ResultRepository;
import com.clinkast.qcm.repositories.TopicRepository;
import com.clinkast.qcm.repositories.UserRepository;
import com.clinkast.qcm.services.api.QcmService;

@Service
public class QcmServiceImpl implements QcmService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    ResultRepository resultRepository;

    @Override
    public Topic createTopic(Topic topic) {
	return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Topic topic) {
	return topicRepository.save(topic);
    }

    @Override
    public Topic getTopicById(int topicId) {
	return topicRepository.findOne(topicId);
    }

    @Override
    public List<Topic> getAllTopics() {
	return topicRepository.findAll();
    }

    @Override
    public Question createQuestion(Question question) {
	return questionRepository.save(question);
    }

    @Override
    public Question update(Question question) {
	return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(int questionId) {
	questionRepository.delete(questionId);

    }

    @Override
    public List<Question> getQuestionsByTopic(int topicId) {
	return questionRepository.findByTopic(topicId);
    }

    @Override
    public List<Question> getQuestionsByTopic(int topicId, int numberOfQuestions) {	
	return getRandomQuestions(questionRepository.findByTopic(topicId),numberOfQuestions);
    }

    @Override
    public List<Question> getQuestionsByTopicAndByLevel(int topicId, int numberOfQuestions, int level) {
	// TODO Auto-generated method stub
	return getRandomQuestions(questionRepository.findByTopicAndLevel(topicId, level),numberOfQuestions);
    }

    @Override
    public Proposition createProposition(Proposition proposition) {
	return propositionRepository.save(proposition);
    }

    @Override
    public Proposition updateProposition(Proposition proposition) {
	return propositionRepository.save(proposition);
    }

    @Override
    public void deleteProposition(int propositionId) {
	propositionRepository.delete(propositionId);

    }

    @Override
    public List<Proposition> getPropostionsQuestion(int questionId) {
	return propositionRepository.findByQuestion(questionId);
    }

    @Override
    public List<Result> getResultsUser(int userId) {
	return resultRepository.findByUser(userId);
    }

    private List<Question> getRandomQuestions(List<Question> questions, int numberOfQuestions) {
	    List<Question> randomQuestions = new ArrayList<Question>();
	    List<Question> copy = new ArrayList<Question>(questions);

	    SecureRandom rand = new SecureRandom();
	    for (int i = 0; i < Math.min(numberOfQuestions, questions.size()); i++) {
	        randomQuestions.add( copy.remove( rand.nextInt( copy.size() )));
	    }

	    return randomQuestions;
	}
}
