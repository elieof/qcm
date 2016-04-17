package com.clinkast.qcm.exception;

import org.springframework.http.HttpStatus;

public class ApiAuthenticationException extends ApiException {

    /**
     * 
     */
    private static final long serialVersionUID = 3686832500604182377L;
    
    private static final String MESSAGE_KEY = ApiErrorCodesEnum.AUHTENTIFICATION_ERROR.getCode();
	
	public ApiAuthenticationException(){
		super("Authentication Error");
		
		this.setStatus(HttpStatus.UNAUTHORIZED);
		this.setMessage(super.getString(MESSAGE_KEY));
		this.setCode(MESSAGE_KEY);
	}
	
	public ApiAuthenticationException(String detail){
	    this();
	    this.setDetail(String.format(getDetail() + ": %s", detail));
	}
}
