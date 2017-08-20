package com.niit.ryver;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.ryver.model.User;
import com.niit.ryver.repository.UserDAO;

//Unit Test
public class UserTest {

	@Autowired
	static AnnotationConfigApplicationContext context;

	@Autowired
	static User user;

	@Autowired
	static UserDAO userDAO;

	@BeforeClass
	static public void initalize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.ryver");
		context.refresh();

		user = context.getBean(User.class);
		System.out.println("user=" + user);

		userDAO = (UserDAO) context.getBean("userDAO");
		System.out.println("UserDAO=" + userDAO);

	}

	@Test
	public void insertPositiveTestCase() {
		user.setEmailId("gautham@gmail.com");
		user.setPassword("Niit@123");
		user.setUserName("gautham");
		user.setIsOnline("true");
		userDAO.insert(user);
		assertEquals("Record Inserted!!", "gautham", user.getUserName());

	}

	@Test
	public void getAllUserPositiveTestCase() {
		int usersCount = userDAO.getAllUsers().size();
		assertEquals("Fetched data!!", usersCount, userDAO.getAllUsers().size());
	}

	@Test
	public void getAllUsersNegativeTestCase() {
		assertFalse("Empty", userDAO.getAllUsers().isEmpty());

	}
}