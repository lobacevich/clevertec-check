package ru.clevertec.check.dao.impl;

import ru.clevertec.check.dao.GenericDao;

import java.util.Optional;

public class AbstractDao<T> implements GenericDao<T> {

    @Override
    public void saveEntity(T t) {

    }

    @Override
    public void updateEntity(T t) {

    }

    @Override
    public void deleteEntity(Long id) {

    }

    @Override
    public Optional<T> getEntityById(Long id) {
        return Optional.empty();
    }
}
