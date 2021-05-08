package org.jarvis.datasource.dao;

public interface GenericDao<T> {
    T insert(T t);
}
