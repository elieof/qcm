package com.clinkast.qcm.utils;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectUtils {
    
    private static final ObjectMapper mapper = new ObjectMapper()
    .enable(SerializationFeature.INDENT_OUTPUT)
    .configure(MapperFeature.USE_ANNOTATIONS, false);


    public static String jsonValue(Object object) throws IOException {
        
        //mapper.setSerializationInclusion(JsonInclude.class);
        return mapper.writeValueAsString(object);
    }

    public static String[] getNullPropertyNames(Object source) {
	final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	return Stream.of(wrappedSource.getPropertyDescriptors())
		.map(FeatureDescriptor::getName)
		.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
		.toArray(String[]::new);
    }
    public static void myCopyProperties(Object src, Object target, String... ignoreProperties) {
	
	BeanUtils.copyProperties(src, target, concatenate(getNullPropertyNames(src), ignoreProperties));
    }
    
    public static <T> T[] concatenate (T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
}
