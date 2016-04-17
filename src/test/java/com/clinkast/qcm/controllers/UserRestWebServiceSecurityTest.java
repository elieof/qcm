package com.clinkast.qcm.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.clinkast.qcm.repositories.RoleRepository;
import com.clinkast.qcm.security.AppUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-spring-config-*.xml"})
public class UserRestWebServiceSecurityTest {

    private MockMvc mockMvc;

    @Resource
    private FilterChainProxy springSecurityFilterChain;
 
    @Autowired
    private AppUserDetailsService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init()  {
	mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
		.addFilter(springSecurityFilterChain)
		.build();
    }

    //with user authentification
    @Test
    public void testgetUserById() throws Exception {
	mockMvc.perform(get("/user/1")
		.accept(MediaType.APPLICATION_JSON)
		.with(user("user")))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$.login", is("admin")));
    }
    
    //with http-basic
    @Test
    public void testgetUserByIdWithBasicAuth() throws Exception {
	 String basicDigestHeaderValue = "Basic " + new String(Base64.encode(("user:user").getBytes()));

	mockMvc.perform(get("/user/1")
		.header("Authorization", basicDigestHeaderValue)
		.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$.login", is("admin")));
    }
   

}


