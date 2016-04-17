package com.clinkast.qcm.exception;

public class ApiInvalidParameterException extends ApiException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_KEY = ApiErrorCodesEnum.BAD_REQUEST_ERROR.getCode();

    public ApiInvalidParameterException(String detail) {
	super(MESSAGE_KEY,getString(MESSAGE_KEY),detail);		

    }
    
    public ApiInvalidParameterException(String message,String detail) {
   	super(MESSAGE_KEY,message,detail);		

       }

}