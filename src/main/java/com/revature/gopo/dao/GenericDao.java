package com.revature.gopo.dao;

import java.util.List;

/**
 * Provides generic methods for dao.
 *
 * @param <T>
 */
//https://www.splessons.com/lesson/hibernate-servlet-integration/
public interface GenericDao <T> {
	List<T> getList();
	T getById(int id);
	List<T> getByUserId(int id);
	void insert(T t);
	void insertOrUpdate(T t);
	void delete(T t);
}
