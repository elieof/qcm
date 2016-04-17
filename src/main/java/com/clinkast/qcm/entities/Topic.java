package com.clinkast.qcm.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topics")
public class Topic extends AbstractEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    public Topic(){
	
    }
    
    public Topic(String name){
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    @Override
    public String toString() {
	return String.format("Topic[%d, %s]", id, name);
    }


}
