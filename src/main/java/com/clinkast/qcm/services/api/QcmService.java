package com.clinkast.qcm.services.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clinkast.qcm.entities.Proposition;
import com.clinkast.qcm.entities.Question;
import com.clinkast.qcm.entities.Result;
import com.clinkast.qcm.entities.Topic;

/**
 * @author Oliver
 *
 */
public interface QcmService {
    
    /**
     * @param userId
     * @return a list of Result for a user
     */
    List<Result> getResultsUser(int userId);
    
    
    //Topic
    /**
     * @param topic
     * @return
     */
    Topic createTopic(Topic topic);
    
    /**
     * @param topic
     * @return a topic
     */
    Topic updateTopic(Topic topic);
    
    /**
     * @param topicId
     * @return
     */
    Topic getTopicById(int topicId);
    
    /**
     * @return a list of all topics
     */
    List<Topic> getAllTopics();
  
    
    //Question
    
    /**
     * @param question
     * @return
     */
    Question createQuestion(Question question);
    
    /**
     * @param question
     * @return
     */
    Question update(Question question);
    
    /**
     * @param questionId
     */
    void deleteQuestion(int questionId);
    
    /**
     * @param topicId
     * @return a list of all the questions available for a topic 
     */
    List<Question> getQuestionsByTopic(int topicId);
    
    /**
     * @param topicId
     * @return a number of questions available for a topic 
     */
    List<Question> getQuestionsByTopic(int topicId, int numberOfQuestions);
    
    /**
     * @param topicId
     * @return a number of questions of a certain level available for a topic 
     */
    List<Question> getQuestionsByTopicAndByLevel(int topicId, int numberOfQuestions, int level);
    
    //Propositions
    
    /**
     * @param proposition
     * @return
     */
    Proposition createProposition(Proposition proposition);
    
    /**
     * @param proposition
     * @return
     */
    Proposition updateProposition(Proposition proposition);
    
    /**
     * @param propositionId
     */
    void deleteProposition(int propositionId);
    
    /**
     * @param questionId
     * @return a list of all propositions available for a question
     */
    List<Proposition> getPropostionsQuestion(int questionId );
    
}
