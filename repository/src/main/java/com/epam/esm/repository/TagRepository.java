package com.epam.esm.repository;

public interface TagRepository<T> extends BaseRepository<T> {
    Long save(T t);
    Boolean getByName(String name);
}
