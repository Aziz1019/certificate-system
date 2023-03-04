package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface BaseService <T>{
    List<T> getAll() throws ResourceNotFoundException;
    T getById(long id) throws ResourceNotFoundException;
    boolean save(T t) throws ServiceException;
    boolean delete(long id) throws ResourceNotFoundException;
}
