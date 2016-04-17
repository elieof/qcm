package com.clinkast.qcm.exception;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception implements Serializable{

    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    private String code;
    private String message;
    private String detail;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", Locale.FRENCH);

    public ApiException(){	
    }

    public ApiException(String message){
	this(null, message);
    }

    public ApiException(String message, String detail){
	this(ApiErrorCodesEnum.GENERIC_ERROR.getCode(), message, detail);
    }

    public ApiException(HttpStatus status, String message, String detail){
	this(status, ApiErrorCodesEnum.GENERIC_ERROR.getCode(), message, detail);
    }

    public ApiException(String code, String message, String detail){
	this(HttpStatus.BAD_REQUEST, code, message, detail);
    }

    public ApiException(HttpStatus statut, String code, String message, String detail){
	super(message);
	this.status = statut;
	this.code = code;
	this.message = message;

	if(message == null){
	    this.message = getString(ApiErrorCodesEnum.GENERIC_ERROR.getCode()); 					
	    this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	this.detail = detail;
    }

    public HttpStatus getStatus() {
	return status;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getDetail() {
	return detail;
    }

    public void setDetail(String detail) {
	this.detail = detail;
    }

    public static String getString(String key){
	if(key == null || !(key instanceof String))
	    return null;

	try{
	    //		String val = bundle.getString(key);
	    //		return new String(val.getBytes("ISO-8859-1"), "UTF-8");
	    return bundle.getString(key);
	}
	catch(MissingResourceException e){
	    e.printStackTrace();
	    return null;
	}
	//	catch (UnsupportedEncodingException e) {
	//		e.printStackTrace();
	//		return null;
	//	}
    }

    public void setStatus(HttpStatus status) {
	this.status = status;
    }
}
