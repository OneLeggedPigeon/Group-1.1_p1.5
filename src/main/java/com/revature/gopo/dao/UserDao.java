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
import org.apache.log4j.Logger;

/**
 * Purpose of this Dao is to send/retrieve info about a user
 * to/from the database. It then returns the composed User Object.
 */
public class UserDao implements GenericDao <User> {
	private static final Logger LOGGER = Logger.getLogger(UserDao.class);

	// TODO: determine if Hibernate's session creation logic goes here or in GenericDao

	private User objectConstructor(ResultSet rs) throws SQLException {
		return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getInt(7));
	}
	
	@Override
	public List<User> getList() {
		// TODO: use Hibernate session.findAll()
		List<User> l = new ArrayList<User>();
		
		try (Connection c = ConnectionUtil.getInstance().getConnection()) {
			String qSql = "SELECT * FROM ers_users";
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(qSql);
			
			while(rs.next()) {
				l.add(objectConstructor(rs));
			}
			LOGGER.debug("A list of users was retrieved from the database.");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get all users from the database failed.");
		}
		return l;
	}

	@Override
	public User getById(int id) {
		// TODO: use Hibernate session.find()
		User u = null;
		
		try(Connection c = ConnectionUtil.getInstance().getConnection()) {
			String qSql = "SELECT * FROM ers_users WHERE ers_users_id = ?";
			PreparedStatement ps = c.prepareStatement(qSql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				u = objectConstructor(rs);
			
			LOGGER.debug("Information about user ID " + id + " was retrieved from the database.");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get info about user ID " + id + " from the database failed.");
		}
		return u;
	}
	
	@Override
	public List<User> getByUserId(int id) {
		// TODO: use Hibernate session.find()
		return null;
	}
	
	@Override
	public User getByUsername(String username) {
		// TODO: use Hibernate session.find()
		User u = null;
		
		try(Connection c = ConnectionUtil.getInstance().getConnection()) {
			String qSql = "SELECT * FROM ers_users WHERE ers_username = ?";
			PreparedStatement ps = c.prepareStatement(qSql);
			ps.setString(1, username.toLowerCase());
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				//System.out.println("User object was created!");
				u = objectConstructor(rs);
			}
			
			LOGGER.debug("Information about username " + username + " was retrieved from the database.");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get info about username " + username + " from the database failed.");
		}
		return u;
	}

	@Override
	public void insert(User t) {
		// TODO: use Hibernate session.save()
		
	}

	@Override
	public void delete(User t) {
		// TODO: use Hibernate session.delete()
		
	}
}
