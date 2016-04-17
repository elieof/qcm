package com.clinkast.qcm.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.repositories.RoleRepository;
import com.clinkast.qcm.security.AppUserDetailsService;
import com.clinkast.qcm.utils.ObjectUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-spring-config-dao.xml", 
"classpath:test-spring-config-services.xml", "classpath:test-spring-config-web.xml"})
public class UserRestWebServiceTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestWebServiceTest.class);

    private MockMvc mockMvc;

    @Autowired
    private AppUserDetailsService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init()  {
	mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
//
//    @Test
//    public void testgetUserById() throws Exception {
//	mockMvc.perform(get("/users/1")
//		.accept(MediaType.APPLICATION_JSON)
//		.contentType(MediaType.APPLICATION_JSON))
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.login", is("adminRest1@test.fr")));
//    }

//    @Test
//    public void testgetAllUsers() throws Exception {
//	mockMvc.perform(get("/users/")
//		.accept(MediaType.APPLICATION_JSON)
//		.contentType(MediaType.APPLICATION_JSON))
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(content().contentType("application/json;charset=UTF-8"))
//		.andExpect(jsonPath("$.[2].user.login", is("user")));
//    }

    @Test
    public void testCreateUser() throws Exception {
	User user = new User("testUser1@yahoo.fr", "testUser1");
	user.getRolesString().add("ROLE_USER");
	LOGGER.debug("user : {}",ObjectUtils.jsonValue(user));
	mockMvc.perform(post("/users/")
		.contentType(MediaType.APPLICATION_JSON)
		.content(ObjectUtils.jsonValue(user))
		.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.login", is("testUser1@yahoo.fr")))
		.andExpect(jsonPath("$.id", not(isEmptyOrNullString())));
    }
}


