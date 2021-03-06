package com.revature.gopo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.gopo.model.User;
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

	/**
	 * Returns a list of all users.
	 *
	 * @return
	 */
	@Override
	public List<User> getList() {
		List<User> result = new ArrayList<User>();
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// watch out for cast from List to List<User>
			result = session.createQuery("from User").list();
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("A list of users was retrieved from the database.");
		} catch (HibernateException | ClassCastException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get all users from the database failed.");
		}

		return result;
	}

	/**
	 * Returns the user with the given ID.
	 *
	 * @param id
	 * @return
	 */
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

	/**
	 * Returns a list containing only the user with the given ID.
	 *
	 * @param id id of the user
	 * @return a size()==1 array of the user with this id
	 */
	@Override
	public List<User> getByUserId(int id) {
		List<User> result = new ArrayList<>();
		result.add(getById(id));
		return result;
	}

	/**
	 * Returns the user with the given username.
	 *
	 * @param username
	 * @return
	 */
	public User getByUsername(String username) {
		User u = null;
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// Actually we can use a @NaturalId to find something with that
			u = session.byNaturalId(User.class).using("username",username).load();
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("Information about username " + username + " was retrieved from the database.");
		} catch (HibernateException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get info about username " + username + " from the database failed.");
		}
		return u;
	}

	/**
	 * Inserts the given user into the db.
	 *
	 * @param t
	 */
	@Override
	public void insert(User t) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Inserts the given user into the db if not present or updates
	 * the matching user with the given user's parameters if present.
	 *
	 * @param user
	 */
	@Override
	public void insertOrUpdate(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// if not present, save; if present, update
		if (getById(user.getUser_id()) == null) {
			session.save(user);
		} else {
			session.update(user);
		}

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Deletes the given user from the db.
	 *
	 * @param t
	 */
	@Override
	public void delete(User t) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		t = session.find(t.getClass(),t.getUser_id());
		if(t != null){
			session.remove(t);
		}
		session.getTransaction().commit();
		session.close();
	}
}
