package com.revature.gopo.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revature.gopo.model.Reimbursement;
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

	/**
	 * Returns a list of all users.
	 *
	 * @return
	 */
	@Override
	public List<Reimbursement> getList() {
		List<Reimbursement> result = new ArrayList<Reimbursement>();
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// watch out for cast from List to List<Reimbursement>
			result = session.createQuery("from Reimbursement").list();
			session.getTransaction().commit();
			session.close();
			LOGGER.debug("All reimbursements were retrieved from the database.");
		} catch (HibernateException | ClassCastException e) {
			e.printStackTrace();
			LOGGER.error("An attempt to get all reimbursements failed.");
		}

		return result;
	}

	/**
	 * Returns the reimbursement object with the given ID.
	 *
	 * @param id
	 * @return
	 */
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

	/**
	 * Returns all reimbursement objects created by the user with the given ID.
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<Reimbursement> getByUserId(int id) {
		List<Reimbursement> l = new ArrayList<Reimbursement>();
		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// watch out for bad casts...maybe?
			l = session.createQuery("from Reimbursement where author = " + id).list();
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

	/**
	 * Inserts the given reimbursement object into the db.
	 *
	 * @param r
	 */
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

	/**
	 * Inserts the given reimbursement object into the db if not present or updates
	 * the matching reimbursement with the given reimbursement's parameters if present.
	 *
	 * @param reimbursement
	 */
	@Override
	public void insertOrUpdate(Reimbursement reimbursement) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// if not present, save; if present, update
		if (getById(reimbursement.getId()) == null) {
			session.save(reimbursement);
		} else {
			session.update(reimbursement);
		}

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Deletes the given reimbursement object from the db.
	 *
	 * @param r
	 */
	@Override
	public void delete(Reimbursement r) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		r = session.get(r.getClass(),r.getId());
		if(r != null) {
			session.delete(r);
		}
		session.getTransaction().commit();
		session.close();
	}
}
