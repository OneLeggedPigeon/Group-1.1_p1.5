package com.revature.gopo.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.revature.gopo.model.User;
import org.apache.log4j.Logger;

import com.revature.gopo.dao.UserDao;

/**
 * Calls UserDao methods to perform persistence actions and handles business logic
 * related to info being persisted to or retrieved from the database.
 */
public class UserService implements GenericService<User> {
	private UserDao ud;
	private static final Logger LOGGER = Logger.getLogger(UserService.class);
	
	public UserService() {
		ud = new UserDao();
	}

	/**
	 * Always creates a new object and database row, mirroring POST functionality
	 *
	 * @param o the User to add to the database
	 */
	@Override
	public void create(Object o) {
		ud.insert((User) o);
	}

	/**
	 * Creates a new object and database row if one is not present;
	 * otherwise, updates the given persistence object (mirroring PUT)
	 *
	 * @param o the User to add to the db or to update
	 */
	@Override
	public void createOrUpdate(Object o) {
		ud.insertOrUpdate((User) o);
	}

	@Override
	public void delete(Object o) {
		ud.delete((User) o);
	}

	@Override
	public List<User> getList() {
		return ud.getList();
	}

	@Override
	public User getById(int id) {
		return ud.getById(id);
	}

	@Override
	public List<User> getByUserId(int id) {
		return ud.getByUserId(id);
	}

	/**
	 * Returns the user specified by the given username with the password
	 * erased for security reasons.
	 *
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username) {
		User u = ud.getByUsername(username);
		if (u != null) {
			u.setPassword(""); //Remove the hashed password for security reasons.
			LOGGER.trace("Password info removed from username " + username + ".");
			return u;
		}
		return null;
	}

	/**
	 * Validates user login. Probably deprecated.
	 *
	 * @param user
	 * @param pass
	 * @return
	 */
	public User getUserByLogin(String user, String pass) {
		User u = ud.getByUsername(user);
		
		if(u != null) {
		String full = user + pass + "salt";
			try {
				//Let MessageDigest know that we want to hash using MD5
				MessageDigest m = MessageDigest.getInstance("md5");
				//Convert our full string to a byte array.
				byte[] messageDigest = m.digest(full.getBytes());
				//Convert our byte array into a signum representation of its former self.
				BigInteger n = new BigInteger(1, messageDigest);
				
				//Convert the whole array into a hexadecimal string.
				String hash = n.toString(16);
				while(hash.length() < 32) {
					hash = "0" + hash;
				}
				
				if(u.getPassword().equals(hash)) {
					//System.out.println("Hash matched!");
					return u;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
