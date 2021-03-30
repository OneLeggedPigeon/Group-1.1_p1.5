package com.revature.service;

import org.apache.log4j.Logger;

import java.util.List;

public interface GenericService<T> {
    public void create(String...strings);
    public void createOrUpdate(String...strings);
    public void delete(String...strings);
    public List<T> getList();
    public T getById(int id);
    public List<T> getByUserId(int id);
}
