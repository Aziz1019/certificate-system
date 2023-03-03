package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

public interface BaseService <T>{
    List<T> getAll();
    Optional<T> getById(long id);
    boolean save(T t);
    boolean delete(long id);
}
