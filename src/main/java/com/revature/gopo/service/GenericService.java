package com.revature.gopo.service;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Defines basic functionality for the service layer of the application.
 *
 * @param <T>
 */
public interface GenericService<T> {
    public void create(Object o);
    public void createOrUpdate(Object o);
    public void delete(Object o);
    public List<T> getList();
    public T getById(int id);
    public List<T> getByUserId(int id);
}
