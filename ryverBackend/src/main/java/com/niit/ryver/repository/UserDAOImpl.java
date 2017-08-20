package com.niit.ryver.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.ryver.model.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionFactory;

	// register
	public User insert(User user) {
		sessionFactory.getCurrentSession().save(user);
		return user;

	}

	// getUser
	public User getUserById(int id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
		return user;

	}

	public List<User> getAllUsers() {
		List<User> user = sessionFactory.getCurrentSession().createQuery("from User").list();
		return user;
	}

	// Login
	public User authenticate(String name, String password) {
		System.out.println("authenticate hit*********");
		String hql = "from User where name = " + "'" + name + "' and " + " password='" + password + "'";
		@SuppressWarnings({ "rawtypes" })
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		sessionFactory.getCurrentSession().flush();

		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		System.out.println("logged in User=" + list.toString());
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		// sessionFactory.getCurrentSession().close();
		return null;
	}

	// Update
	public boolean update(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
