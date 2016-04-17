package com.clinkast.qcm.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);

    private static final String BAD_REQUEST_KEY = ApiErrorCodesEnum.BAD_REQUEST_ERROR.getCode();
    private static final String NOT_FOUND_KEY = ApiErrorCodesEnum.NOT_FOUND.getCode();
    private static final String GENERIC_ERROR_KEY = ApiErrorCodesEnum.GENERIC_ERROR.getCode();


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
	    HttpHeaders headers, HttpStatus status, WebRequest request) {
	LOGGER.error(ex.getMessage(), ex);

	String code = GENERIC_ERROR_KEY;
	if(HttpStatus.BAD_REQUEST.equals(status) || HttpStatus.UNSUPPORTED_MEDIA_TYPE.equals(status)) code = BAD_REQUEST_KEY;
	if(HttpStatus.NOT_FOUND.equals(status)) code = NOT_FOUND_KEY;

	//	if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
	//		request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
	//	}
	ApiErrorMessage errorMessage = new ApiErrorMessage(status, code, ApiException.getString(code), ex.getMessage() );
	return new ResponseEntity<Object>(errorMessage, headers, status);
    } 


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

	LOGGER.debug(ex.getMessage());	
	List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
	List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

	ValidationResult result = new ValidationResult();
	for (FieldError fieldError : fieldErrors) {
	    result.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
	}
	for (ObjectError objectError : globalErrors) {
	    result.addValidationError(objectError.getObjectName(),objectError.getDefaultMessage());
	}

	ApiErrorMessage errorMessage = new ApiErrorMessage(status, BAD_REQUEST_KEY, ApiException.getString(BAD_REQUEST_KEY), result);
	return new ResponseEntity<Object>(errorMessage, headers, status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> errorHandler(ApiException e) {
	LOGGER.error(e.getMessage(), e);
	return new ResponseEntity<>(new ApiErrorMessage(e), e.getStatus());	
    }


    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> errorHandler(ConstraintViolationException  e) {
	LOGGER.debug(e.getMessage());	

	ValidationResult result = new ValidationResult();
	for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
	    String key = "";
	    if (violation.getPropertyPath() != null) {
		key = violation.getPropertyPath().toString();
	    }
	    result.addValidationError(key, violation.getMessage());
	}

	ApiErrorMessage errorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, BAD_REQUEST_KEY, ApiException.getString(BAD_REQUEST_KEY), result);
	return new ResponseEntity<>(errorMessage,  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> errorHandler(AccessDeniedException  e) {
	LOGGER.debug(e.getMessage());	

	ApiErrorMessage errorMessage = new ApiErrorMessage(new ApiUnauthorizedException());
	return new ResponseEntity<>(errorMessage,  HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> errorHandler(Exception e) {
	LOGGER.error(e.getMessage(), e);

	ApiErrorMessage errorMessage = new ApiErrorMessage();	
	String errorMsg = e.getMessage()!=null && e.getMessage().length()>10 ? e.getMessage() :ApiException.getString(ApiErrorCodesEnum.GENERIC_ERROR.getCode());
	StringWriter errorStackTrace = new StringWriter();
	e.printStackTrace(new PrintWriter(errorStackTrace));

	errorMessage.setMessage(errorMsg);	
	errorMessage.setCode(ApiErrorCodesEnum.GENERIC_ERROR.getCode());
	errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	errorMessage.setDetail(errorStackTrace.toString());
	return new ResponseEntity<>(errorMessage,  HttpStatus.INTERNAL_SERVER_ERROR);

    }


}