package com.expatrio.usermanagement.repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Jooq repository.
 *
 * @param <T> the type parameter
 */
public interface JOOQRepository<T> {

    /**
     * The constant DEFAULT_PAGE_SIZE.
     */
    int DEFAULT_PAGE_SIZE = 10;
    /**
     * The constant DEFAULT_PAGE_NUMBER.
     */
    int DEFAULT_PAGE_NUMBER = 0;

    /**
     * Save t.
     *
     * @param entity the entity
     * @return the t
     */
    T save(T entity);

    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     */
    T update(T entity);

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteById(Long id);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(Long id);

    /**
     * Find all list.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list
     */
    List<T> findAll(Integer page, Integer pageSize);
}
