package com.revature.gopo.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.revature.gopo.model.Reimbursement;
import com.revature.gopo.model.User;
import com.revature.gopo.util.ConnectionUtil;
import com.revature.gopo.util.SessionUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Purpose of this Dao is to send/retrieve info about a reimbursement
 * to/from the database. It then returns the composed Reimbursement Object.
 */
public class ReimbursementDao implements GenericDao<Reimbursement> {
	private static final Logger LOGGER = Logger.getLogger(ReimbursementDao.class);
	private SessionFactory sessionFactory;

	public ReimbursementDao() {
		sessionFactory = SessionUtil.getSessionFactory();
	}
	
	private Reimbursement objectConstructor(ResultSet rs) throws SQLException {
		return new Reimbursement(rs.getInt(1), rs.getFloat(2), rs.getTimestamp(3), rs.getTimestamp(4),
							rs.getString(5), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
	}

	@Override
	public List<Reimbursement> getList() {
		List<Reimbursement> result = new ArrayList<Reimbursement>();
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// watch out for cast from List to List<Reimbursement>
			result = session.createQuery("from Reimbursements").list();
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("All reimbursements were retrieved from the database.");
		} catch (HibernateException | ClassCastException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get all reimbursements failed.");
		}

		return result;
	}

	@Override
	public Reimbursement getById(int id) {
		Reimbursement r = null;
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// using Hibernate's get() method instead of JPA's find() method
			r = session.get(Reimbursement.class, id);
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("A reimbursement by ID " + id + " was retrieved from the database.");
		} catch (HibernateException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get a reimbursement by ID" + id + " from the database failed.");
		}

		return r;
	}
	
	@Override
	public List<Reimbursement> getByUserId(int id) {
		List<Reimbursement> l = new ArrayList<Reimbursement>();
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// watch out for bad casts...maybe?
			l = session.createQuery("from Reimbursements where id = " + id).list();
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("A list of reimbursements made by user ID " + id + " was retrieved from the database.");
		} catch (HibernateException | ClassCastException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get all reimbursements made by user ID " + id + " fron the database failed.");
		}

		// why is this here?
		System.out.println(l.toString());
		return l;
	}
	
	public Reimbursement getByUsername(String username) {
		//Empty. Reason - No use.
		return null;
	}

	@Override
	public void insert(Reimbursement r) {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(r);
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("A new reimbursement was successfully added to the database.");
		} catch (HibernateException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to insert a reimbursement to the database failed.");
		}
	}
	
	public void updateList(int[][] i, int resolver) {
		// TODO: use Hibernate session.update()
		try(Connection c = ConnectionUtil.getInstance().getConnection()) {
			String aSql = "SELECT acceptarray(?, ?)";
			String dSql = "SELECT denyarray(?, ?)";
			
			//Convert both of our int arrays to an Integer object
			Integer[] a = Arrays.stream(i[0]).boxed().toArray(Integer[]::new);
			Integer[] d = Arrays.stream(i[1]).boxed().toArray(Integer[]::new);
			
			//Convert both of our Integer arrays into something useful for SQL.
			Array aArray = c.createArrayOf("INTEGER", a);
			Array dArray = c.createArrayOf("INTEGER", d);
			
			//Perform our SQL calls
			CallableStatement cs = c.prepareCall(aSql);
			cs.setArray(1, aArray);
			cs.setInt(2, resolver);
			cs.execute();
			cs.closeOnCompletion();
			
			cs = c.prepareCall(dSql);
			cs.setArray(1, dArray);
			cs.setInt(2, resolver);
			cs.execute();
			cs.closeOnCompletion();
			
			//This section is just for the sake of logging.
			int totalCount = 0;
			for(int co = 0; co < a.length; co++) {
				if (a[co] != -1) {
					totalCount++;
				}
				if (d[co] != -1) {
					totalCount++;
				}
			}
			LOGGER.debug(totalCount + " reimbursement" + ((totalCount != 1) ? "s" : "") + " modified by user ID " + resolver + ".");
		} catch (SQLException e) {
			LOGGER.error("An attempt to accept/deny reimbursements by user ID " + resolver + " from the database failed.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Reimbursement r) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(r);
		session.getTransaction().commit();
		session.close();
	}
	
}
