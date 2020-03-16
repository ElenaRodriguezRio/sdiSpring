package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.entities.*;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.LoginValidator;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	

	@RequestMapping("/user/list")
	public String getListado(Model model, @RequestParam(value="", required=false) String searchText, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText!=null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailNameAndLastName(searchText, pageable, user);
		} else {
			users = usersService.getUsers(pageable, user);
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list";
	}
	
	@RequestMapping("/user/listAdmin")
	public String getListadoModoAdministrador(Model model, @RequestParam(value="", required=false) String searchText, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText!=null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailNameAndLastName(searchText, pageable, user);
		} else {
			users = usersService.getUsers(pageable, user);
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/listAdmin";
	}


	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/listAdmin";
	}


	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]); //registramos un usuario con rol de usuario estándar
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error!=null) {
			model.addAttribute("error", "");
		}
		if (logout != null)
            model.addAttribute("message", "");
		return "login";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

}
