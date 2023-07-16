package com.expatrio.usermanagement.repository;

import java.util.List;
import java.util.Optional;

public interface JOOQRepository<T> {

    int DEFAULT_PAGE_SIZE = 10;
    int DEFAULT_PAGE_NUMBER = 0;

    T save(T entity);

    T update(T entity);

    boolean deleteById(Long id);

    Optional<T> findById(Long id);

    List<T> findAll(Integer page, Integer pageSize);
}
