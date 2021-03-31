package com.revature.gopo.dao;

import java.util.List;

/**
 * Provides generic methods for dao. Also handles Hibernate session
 * creation and transaction control methods.
 *
 * @param <T>
 */
//https://www.splessons.com/lesson/hibernate-servlet-integration/
public interface GenericDao <T> {
	// TODO: potentially implement Hibernate session logic here
	List<T> getList();
	T getById(int id);
	List<T> getByUserId(int id);
	T getByUsername(String username);
	void insert(T t);
	void delete(T t);
}