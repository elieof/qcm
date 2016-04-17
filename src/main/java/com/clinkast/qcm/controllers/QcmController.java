package com.clinkast.qcm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clinkast.qcm.entities.Question;
import com.clinkast.qcm.security.AppUserDetailsService;
import com.clinkast.qcm.services.api.QcmService;

@RestController
@RequestMapping("/question")
public class QcmController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);
    
    @Autowired
    private QcmService qcmService;
    
    @RequestMapping(value="/random/{idTopic}/{numberOfQuestions}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Question> getQuestions(@PathVariable("idTopic") int topicId, @PathVariable("numberOfQuestions") int numberOfQuestions) {
        return qcmService.getQuestionsByTopic(topicId, numberOfQuestions);
    }
    
    @RequestMapping(value="/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Question createQuestion(Question question) {
        return qcmService.createQuestion(question);
    }
    
    @RequestMapping(value="/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Question updateQuestion(Question question) {
        return qcmService.createQuestion(question);
    }
    
    @RequestMapping(value="/delete/{questionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("idTopic") int questionId) {
        qcmService.deleteQuestion(questionId);
    }
    
    @RequestMapping(value="/proposition/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Question updateQuestionProposition(Question question) {
        return qcmService.createQuestion(question);
    }
    
    @RequestMapping(value="/proposition/delete/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProposition(@PathVariable("id") int propositionId) {
        qcmService.deleteProposition(propositionId);
    }
    
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> errorHandler(Exception exc) {
//        LOGGER.error(exc.getMessage(), exc);
//        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
//    }

}
