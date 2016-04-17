package com.clinkast.qcm.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidationResult { 
    
    private Map<String, List<String>> validationErrors = new HashMap<>();

    public Map<String, List<String>> getValidationErrors() { 
        return validationErrors; 
    } 
 
    public void setValidationErrors(Map<String, List<String>> validationErrors) { 
        this.validationErrors = validationErrors; 
    } 
 
    public void addValidationError(String field, String message) {
	
	if(validationErrors.containsKey(field)){
	    validationErrors.get(field).add(message);
	    return;
	}
	List<String>errors = new ArrayList<String>();
	errors.add(message);
        validationErrors.put(field, errors); 
    } 
 
    public String toJson(){
	try {
	    return new ObjectMapper().writeValueAsString(this);
	} catch (JsonProcessingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }
}