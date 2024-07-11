package ru.clevertec.check.dao;

import java.util.Optional;

public interface GenericDao<T> {

    void saveEntity(T t);

    void updateEntity(T t);

    void deleteEntity(Long id);

    Optional<T> getEntityById(Long id);
}
