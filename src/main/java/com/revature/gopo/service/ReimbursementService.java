package com.revature.gopo.service;

import java.util.List;
import java.util.Map;

import com.revature.gopo.model.Reimbursement;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.gopo.dao.ReimbursementDao;

/**
 * Calls ReimbursementDao methods to perform persistence actions and handles business logic
 * related to info being persisted to or retrieved from the database.
 */
public class ReimbursementService implements GenericService<Reimbursement> {
	private ReimbursementDao rd;
	private static final Logger LOGGER = Logger.getLogger(ReimbursementService.class);
	
	public ReimbursementService() {
		rd = new ReimbursementDao();
	}

	/**
	 * Always creates a new object and database row, mirroring POST functionality
	 *
	 * @param o the Reimbursement to add to the database
	 */
	@Override
	public void create(Object o) {
		//TODO: Differentiate this from createOrUpdate
		rd.insert((Reimbursement) o);
	}

	/**
	 * Creates a new object and database row if one is not present;
	 * otherwise, updates the given persistence object (mirroring PUT)
	 *
	 * @param o the User to add to the db or to update
	 */
	@Override
	public void createOrUpdate(Object o) {
		//TODO: Differentiate this from create
		rd.insertOrUpdate((Reimbursement) o);
	}

	@Override
	public void delete(Object o) {
		rd.delete((Reimbursement) o);
	}

	@Override
	public List<Reimbursement> getList() {
		return rd.getList();
	}

	@Override
	public Reimbursement getById(int id) {
		return rd.getById(id);
	}

	@Override
	public List<Reimbursement> getByUserId(int id) {
		return rd.getByUserId(id);
	}
}
