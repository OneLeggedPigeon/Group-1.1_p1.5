package com.revature.dao;

import java.util.List;

/**
 * Provides generic methods for dao. Also handles Hibernate session
 * creation and transaction control methods.
 *
 * @param <T>
 */
public interface GenericDao <T> {
	// TODO: potentially include Hibernate session methods here
	List<T> getList();
	T getById(int id);
	List<T> getByUserId(int id);
	T getByUsername(String username);
	void insert(T t);
	void delete(T t);
}
