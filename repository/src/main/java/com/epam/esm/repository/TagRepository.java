package com.epam.esm.repository;

public interface TagRepository<T> extends BaseRepository<T> {
    boolean save(T t);
}
