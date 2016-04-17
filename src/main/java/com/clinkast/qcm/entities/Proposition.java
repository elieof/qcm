package com.clinkast.qcm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "propositions")
public class Proposition extends AbstractEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String statement;

    private boolean correct;

    @Lob 
    private String explanation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    // clé étrangère
    @Column(name = "question_id", insertable = false, updatable = false)
    private Integer questionId;

    public Proposition(){

    }

    public Proposition(Question question, String statement, boolean correct, String explanation){
	this.question = question;
	this.statement = statement;
	this.correct = correct;
	this.explanation = explanation;
    }

    @Override
    public String toString() {
	return String.format("Proposition[%d, %d, %s, %s, %s]", id, questionId, statement, correct, explanation);
    }
    
    public String getStatement() {
	return statement;
    }

    public void setStatement(String statement) {
	this.statement = statement;
    }

    public boolean isCorrect() {
	return correct;
    }

    public void setCorrect(boolean correct) {
	this.correct = correct;
    }

    public String getExplanation() {
	return explanation;
    }

    public void setExplanation(String explanation) {
	this.explanation = explanation;
    }

    public Question getQuestion() {
	return question;
    }

    public void setQuestion(Question question) {
	this.question = question;
    }
}
