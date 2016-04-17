package com.clinkast.qcm.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clinkast.qcm.entities.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-spring-config-dao.xml","classpath:test-spring-config-security.xml"})
public class RepositoriesTest {

    @Autowired
    private PropositionRepository propositionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;    
    @Autowired
    private RoleRepository roleRepository;

    
//    @Test
//    public void findUserByLogin() {
//	// on récupère l'utilisateur [admin]
//	User user = userRepository.findUserByLogin("admin");
//	// on vérifie que son mot de passe est [admin]
//	Assert.assertTrue(BCrypt.checkpw("admin", user.getPassword()));
//	// on vérifie le rôle de admin / admin
//	List<Role> roles = userRepository.getRoles("admin", user.getPassword());
//	Assert.assertEquals(1, roles.size());
//	Assert.assertEquals("ROLE_ADMIN", roles.get(0).getName());
//    }
    
    @Test
    public void getQuestionsByTopic(){
	for(Question question :questionRepository.findByTopic(1)){
	    System.out.println(question);
	    Assert.assertEquals(1, (int)question.getTopicId());
	    Assert.assertEquals("Question statement 1", question.getStatement());
	    Assert.assertEquals(3, question.getPropositions().size());
	    Assert.assertEquals("Statement3", question.getPropositions().get(2).getStatement());
	};
    }

}

