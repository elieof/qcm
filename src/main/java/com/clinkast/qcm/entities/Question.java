package com.clinkast.qcm.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Question extends AbstractEntity {


    private static final long serialVersionUID = 1L;    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    
 // clé étrangère
    @Column(name = "topic_id", insertable = false, updatable = false)
    private Integer topicId;
    
    @Lob 
    private String statement;
    
    private Integer level;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="question",fetch = FetchType.EAGER) 
    private List<Proposition> propositions;
    
    public Question(){
	
    }
    
    public Question(Topic topic, String statement, Integer level, List<Proposition> propositions){
	this.topic = topic;
	this.statement = statement;
	this.level = level;
	this.propositions = propositions;
    }
    
    @Override
    public String toString() {
	return String.format("Question[%d, %d, %s, %d, [%s]]", id, topicId, statement, level, propositions);
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<Proposition> propositions) {
        this.propositions = propositions;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

}
