package com.clinkast.qcm.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.clinkast.qcm.entities.Proposition;
import com.clinkast.qcm.entities.Question;
import com.clinkast.qcm.entities.Role;
import com.clinkast.qcm.entities.Topic;
import com.clinkast.qcm.entities.User;
import com.clinkast.qcm.entities.UserRole;
import com.clinkast.qcm.repositories.PropositionRepository;
import com.clinkast.qcm.repositories.QuestionRepository;
import com.clinkast.qcm.repositories.RoleRepository;
import com.clinkast.qcm.repositories.TopicRepository;
import com.clinkast.qcm.repositories.UserRepository;

public class TestDataInitializer {

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

    public void init() throws Exception {
	//Role 
	String[] roleNames = {"admin", "rh", "user"};
	for(String roleName : roleNames){
	    String crypt = BCrypt.hashpw(roleName, BCrypt.gensalt());
	    User user =  new User(roleName+"@yahoo.fr", crypt);
	    user.getRoles().add(new Role("ROLE_"+roleName.toUpperCase()));
	    userRepository.save(user);
	}

	//Topic
	String topicName = "Java";
	String [] propositionStatements = {"Statement1", "Statement2", "Statement3"};
	Topic topic = topicRepository.save(new Topic(topicName));
	List<Proposition> propositions = new ArrayList<Proposition>();
	Question question = new Question(topic,"Question statement 1",1,propositions);
	int i = 0;
	for(String propositionStatement: propositionStatements){
	    boolean correct= false;
	    if(i==0) correct = true;
	    Proposition proposition = new Proposition(question, propositionStatement, correct, "");
	    propositions.add(proposition);
	    i++;
	}

	questionRepository.save(question);
    }

}
