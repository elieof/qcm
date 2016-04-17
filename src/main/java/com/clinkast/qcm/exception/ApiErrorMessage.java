package com.clinkast.qcm.exception;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement
@XmlType(propOrder = {"status", "code", "message", "detail"})
public class ApiErrorMessage {

    HttpStatus status;
    String code;
    String message;
    Object detail;	

    /** contains the same HTTP Status code returned by the server */
    public HttpStatus getStatus() {
	return status;
    }

    public void setStatus(HttpStatus status) {
	this.status = status;
    }

    /** application specific error code */
    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    /** message describing the error*/
    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    /** extra information that might useful for developers */
    public Object getDetail() {
	return detail;
    }

    public void setDetail(Object detail) {
	this.detail = detail;
    }


    public ApiErrorMessage() {}

    public ApiErrorMessage(ApiException ex){
	this.status = ex.getStatus();
	this.message = ex.getMessage();
	this.code = ex.getCode();
	this.detail = ex.getDetail();
    }

    public ApiErrorMessage(HttpStatus status, String code, String message, Object detail){
	this.status = status;
	this.message = message;
	this.code = code;
	this.detail = detail;
    }


    public String jsonValue(){
	try {
	    return  new ObjectMapper().writeValueAsString(this);
	} catch (JsonProcessingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }

    @Override
    public String toString() {
	return String.format("ApiErrorMessage[%s, %s, %s, %s]", status, code, message, detail);
    }

}
