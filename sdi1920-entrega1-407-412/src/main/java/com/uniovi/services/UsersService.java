package com.uniovi.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 


	@PostConstruct
	public void init() {

	}
	
	public Page<User> getUsers(Pageable pageable, User user) {
		Page<User> users = usersRepository.searchStandardUsersButAuthenticated(user.getId(), pageable);
		return users;
	}

	public Page<User> getAllUsers(Pageable pageable) {
		Page<User> users = usersRepository.findAll(pageable);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
	public Page<User> searchUsersByNameAndLastName(String searchText, Pageable pageable) {
		Page<User> users = usersRepository.searchByNameAndLastName(searchText, pageable);
		return users;
	}

}
