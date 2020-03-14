package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private FriendRequestsService friendRequestsService;
	
	@Autowired
	private FriendsService friendsService;

	@PostConstruct
	public void init() {
		//Usuario normal (ROLE_STANDARDUSER)
		User user1 = new User("pedro99@uniovi.es", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		//Usuario admin (ROLE_ADMIN)
		User user2 = new User("admin@email.com", "Marta", "Almonte");
		user2.setPassword("admin");
		user2.setRole(rolesService.getRoles()[1]);
		//Usuario normal (ROLE_STANDARDUSER)
		User user3 = new User("marta@uniovi.es", "Marta", "Díaz");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		//Usuario normal (ROLE_STANDARDUSER)
		User user4 = new User("carmen98@uniovi.es", "Carmen", "García");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		//Usuario normal (ROLE_STANDARDUSER)
		User user5 = new User("alejandroo@uniovi.es", "Alejandro", "González");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		//Usuario normal (ROLE_STANDARDUSER)
		User user6 = new User("carlos@uniovi.es", "Carlos", "Antuña");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		//Usuario normal (ROLE_STANDARDUSER)
		User user7 = new User("irene@uniovi.es", "Irene", "Rodríguez");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[0]);
		//Usuario normal (ROLE_STANDARDUSER)
		User user8 = new User("jose@uniovi.es", "José", "Fernández");
		user8.setPassword("123456");
		user8.setRole(rolesService.getRoles()[0]);

		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);
		
		friendsService.addFriend(new Friend(user1.getId(),user2.getId()));
		friendsService.addFriend(new Friend(user2.getId(),user3.getId()));
		friendsService.addFriend(new Friend(user2.getId(),user4.getId()));
		friendRequestsService.addFriendRequest(new FriendRequest(user5.getId(),user6.getId()));
	}
}
