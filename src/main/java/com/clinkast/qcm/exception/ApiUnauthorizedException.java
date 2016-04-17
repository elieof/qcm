package com.clinkast.qcm.exception;

import org.springframework.http.HttpStatus;

public class ApiUnauthorizedException extends ApiException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_KEY = ApiErrorCodesEnum.UNAUTHORIZED_ERROR.getCode();
	
	public ApiUnauthorizedException(){
		super("Access Denied");
		
		this.setStatus(HttpStatus.FORBIDDEN);
		this.setMessage(super.getString(MESSAGE_KEY));
		this.setCode(MESSAGE_KEY);
	}
	
	public ApiUnauthorizedException(String detail){
	    this();
	    this.setDetail(String.format(getDetail() + ": %s", detail));
	}
	
	

}
