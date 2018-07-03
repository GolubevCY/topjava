package ru.javawebinar.topjava.repository;


import java.util.List;

public interface Repository<T, I> {
    void add(T item);

    void delete(I id);

    void update(T item);

    List<T> getAll();

    T getById(I id);
}