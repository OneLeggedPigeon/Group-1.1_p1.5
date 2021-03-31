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
	
	public void createReimbursement(String json) {
		try {
			Reimbursement r = new ObjectMapper().readValue(json, Reimbursement.class);
			LOGGER.debug("JSON from the client was successfully parsed.");
			rd.insert(r);
		} catch (Exception e) {
			LOGGER.error("Something occurred during JSON parsing for a new reimbursement. Is the JSON malformed?");
			e.printStackTrace();
		}
	}
	
	public List<Reimbursement> fetchAllReimbursements() {
		return rd.getList();
	}
	
	public List<Reimbursement> getReimbursementsByUserID(int id) {
		return rd.getByUserId(id);
	}
	
	public void updateReimbursements(int[][] i, int r) {
		rd.updateList(i, r);
	}

	@Override
	public void create(Map<String, String> map) {

	}

	@Override
	public void createOrUpdate(Map<String, String> map) {

	}

	@Override
	public void delete(Map<String, String> map) {

	}

	@Override
	public List<Reimbursement> getList() {
		return null;
	}

	@Override
	public Reimbursement getById(int id) {
		return null;
	}

	@Override
	public List<Reimbursement> getByUserId(int id) {
		return null;
	}
}
