package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

import java.util.List;

/**
 * The BaseService interface provides a set of methods to retrieve, create, update, and delete resources of type T.
 *
 * @param <T> The type of the resource.
 */

public interface BaseService<T> {
    List<T> getAll() throws ResourceNotFoundException;

    T getById(long id) throws ResourceNotFoundException;

    void save(T t) throws ServiceException;

    void delete(long id) throws ResourceNotFoundException, ServiceException;
}
