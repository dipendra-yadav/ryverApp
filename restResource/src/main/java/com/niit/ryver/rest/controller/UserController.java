package com.niit.ryver.rest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.ryver.model.User;

import com.niit.ryver.repository.UserDAO;

@RestController
public class UserController {

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@Autowired
	HttpSession session;

	// register
	// client is sending the data in JSON format. This method has to convert
	// JSON to JAVA
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) {

		try {

			User savedUser = userDAO.insert(user);
			if (savedUser.getId() == 0) {
				com.niit.ryver.model.Error error = new com.niit.ryver.model.Error(2, "Couldnt insert user details ");
				return new ResponseEntity<com.niit.ryver.model.Error>(error, HttpStatus.CONFLICT);
			} else
				return new ResponseEntity<User>(savedUser, HttpStatus.OK);

		}

		catch (Exception e) {
			e.printStackTrace();
			com.niit.ryver.model.Error error = new com.niit.ryver.model.Error(2,
					"Couldnt insert user details. Cannot have null/duplicate values " + e.getMessage());
			return new ResponseEntity<com.niit.ryver.model.Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/get/alluser", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUser() {
		List<User> users = userDAO.getAllUsers();
		if (users.isEmpty()) {

			com.niit.ryver.model.Error error = new com.niit.ryver.model.Error(1, "There is no users");
			return new ResponseEntity<com.niit.ryver.model.Error>(error, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);

		}

	}

	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session) {

		User user = (User) session.getAttribute("user"); // only for
															// authentication
		if (user == null) {
			com.niit.ryver.model.Error error = new com.niit.ryver.model.Error(3, "unauthorized user");
			return new ResponseEntity<com.niit.ryver.model.Error>(error, HttpStatus.UNAUTHORIZED);
		} else {
			user = userDAO.getUserById(user.getId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}

	// login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {

		User validUser = userDAO.authenticate(user.getUserName(), user.getPassword());
		if (validUser == null) {
			// logger.debug("validUser is null");
			com.niit.ryver.model.Error error = new com.niit.ryver.model.Error(1,
					"Username and password doesn't exists...");
			return new ResponseEntity<com.niit.ryver.model.Error>(error, HttpStatus.UNAUTHORIZED);
		} else {
			session.setAttribute("user", validUser);

			validUser.setIsOnline("true");

			userDAO.update(validUser);

			// logger.debug("validUser is not null");

			return new ResponseEntity<User>(validUser, HttpStatus.OK);
		}

	}

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			user.setIsOnline("false");
			userDAO.update(user);
		}
		session.removeAttribute("user");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
