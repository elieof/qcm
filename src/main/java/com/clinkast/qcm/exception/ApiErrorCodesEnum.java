package com.clinkast.qcm.exception;

public enum ApiErrorCodesEnum {
    GENERIC_ERROR("ERROR.SERVER"),
    BAD_REQUEST_ERROR("ERROR.BAD_REQUEST"),
    AUHTENTIFICATION_ERROR("ERROR.UNAUTHORIZED"),
    NOT_FOUND("ERROR.NOT_FOUND"),
    UNAUTHORIZED_ERROR("ERROR.FORBIDDEN");


    private String code;

    private ApiErrorCodesEnum(String code){
	this.code = code;
    }

    public String getCode() {
	return code;
    }

}
