//package com.clinkast.qcm.exception;
//
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//@SuppressWarnings("serial")
//@Component
//public class CustomObjectMapper extends ObjectMapper {
//    
//    public CustomObjectMapper() {
//	 setSerializationInclusion(JsonInclude.Include.NON_NULL); 
//	 configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	 enable(SerializationFeature.INDENT_OUTPUT);
//    }
//}