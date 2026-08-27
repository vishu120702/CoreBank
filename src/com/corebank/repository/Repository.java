package com.corebank.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T,ID> {
    // void save(ID id, T entity);
    T save(ID id, T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
