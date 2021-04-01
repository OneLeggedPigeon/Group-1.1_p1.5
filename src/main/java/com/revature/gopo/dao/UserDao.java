package com.revature.gopo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.gopo.model.User;
import com.revature.gopo.util.ConnectionUtil;
import com.revature.gopo.util.SessionUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Purpose of this Dao is to send/retrieve info about a user
 * to/from the database. It then returns the composed User Object.
 */
public class UserDao implements GenericDao <User> {
	private static final Logger LOGGER = Logger.getLogger(UserDao.class);
	private SessionFactory sessionFactory;

	public UserDao() {
		sessionFactory = SessionUtil.getSessionFactory();
	}

	private User objectConstructor(ResultSet rs) throws SQLException {
		return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getInt(7));
	}
	
	@Override
	public List<User> getList() {
		List<User> result = new ArrayList<User>();
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// watch out for cast from List to List<User>
			result = session.createQuery("from Users").list();
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("A list of users was retrieved from the database.");
		} catch (HibernateException | ClassCastException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get all users from the database failed.");
		}

		return result;
	}

	@Override
	public User getById(int id) {
		User u = null;
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// using Hibernate's get() method instead of JPA's find() method
			u = session.get(User.class, id);
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("Information about user ID " + id + " was retrieved from the database.");
		} catch (HibernateException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get info about user ID " + id + " from the database failed.");
		}

		return u;
	}
	
	@Override
	public List<User> getByUserId(int id) {
		// Empty. Reason - no use. Users' id is primary key;
		// therefore getting a user by id will never return more than 1 user.
		return null;
	}
	
	@Override
	public User getByUsername(String username) {
		User u = null;
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// using Hibernate's get() method instead of JPA's find() method
			// also works with strings because they're serializable!
			u = session.get(User.class, username);
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("Information about username " + username + " was retrieved from the database.");
		} catch (HibernateException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get info about username " + username + " from the database failed.");
		}
		return u;
	}

	@Override
	public void insert(User t) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(User t) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(t);
		session.getTransaction().commit();
		session.close();
	}
}
