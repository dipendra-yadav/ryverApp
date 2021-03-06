package com.niit.ryver.repository;

import java.util.List;

import com.niit.ryver.model.User;

public interface UserDAO {
	public User insert(User user);

	public User getUserById(int id);

	public List<User> getAllUsers();

	public User authenticate(String name, String Password);

	public boolean update(User user);


}
