package com.revature.service;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public interface GenericService<T> {
    public void create(Map<String,String> map);
    public void createOrUpdate(Map<String,String> map);
    public void delete(Map<String,String> map);
    public List<T> getList();
    public T getById(int id);
    public List<T> getByUserId(int id);
}
