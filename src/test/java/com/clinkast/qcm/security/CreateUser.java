//package com.clinkast.qcm.security;
//
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//
//import com.clinkast.qcm.entities.Role;
//import com.clinkast.qcm.entities.User;
//import com.clinkast.qcm.entities.UserRole;
//import com.clinkast.qcm.repositories.RoleRepository;
//import com.clinkast.qcm.repositories.UserRepository;
//import com.clinkast.qcm.repositories.UserRoleRepository;
//
//public class CreateUser {
//
//    public static void main(String[] args) {
//	// syntaxe : login password roleName
//
//	// il faut trois paramètres
//	if (args.length != 3) {
//	    System.out.println("Syntaxe : [pg] user password role");
//	    System.exit(0);
//	}
//	// on récupère les paramètres
//	String login = args[0];
//	String password = args[1];
//	String roleName = String.format("ROLE_%s", args[2].toUpperCase());
//	// contexte Spring
//	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config-dao.xml");
//	UserRepository userRepository = context.getBean(UserRepository.class);
//	RoleRepository roleRepository = context.getBean(RoleRepository.class);
//	UserRoleRepository userRoleRepository = context.getBean(UserRoleRepository.class);
//	// le rôle existe-t-il déjà ?
//	Role role = roleRepository.findRoleByName(roleName);
//	// s'il n'existe pas on le crée
//	if (role == null) {
//	    role = roleRepository.save(new Role(roleName));
//	}
//	// l'utilisateur existe-t-il déjà ?
//	User user = userRepository.findByLogin(login);
//	// s'il n'existe pas on le crée
//	if (user == null) {
//	    // on hashe le mot de passe avec bcrypt
//	    String crypt = BCrypt.hashpw(password, BCrypt.gensalt());
//	    // on sauvegarde l'utilisateur
//	    user = userRepository.save(new User(login, login, crypt));
//	    // on crée la relation avec le rôle
//	    userRoleRepository.save(new UserRole(user, role));
//	} else {
//	    // l'utilisateur existe déjà- a-t-il le rôle demandé ?
//	    boolean trouvé = false;
//	    for (Role r : userRepository.getRoles(user.getId())) {
//		if (r.getName().equals(roleName)) {
//		    trouvé = true;
//		    break;
//		}
//	    }
//	    // si pas trouvé, on crée la relation avec le rôle
//	    if (!trouvé) {
//		userRoleRepository.save(new UserRole(user, role));
//	    }
//	}
//
//	// fermeture contexte Spring
//	context.close();
//    }
//
//}
