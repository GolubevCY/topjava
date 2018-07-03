package ru.javawebinar.topjava.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;


public class InMemoryMealRepository implements Repository<Meal, Long> {
    private final AtomicLong counter = new AtomicLong(0);

    private final ConcurrentMap<Long, Meal> inMemoryData = new ConcurrentHashMap<>();

    @Override
    public void add(@NotNull Meal item) {
        item.setId(counter.getAndIncrement());
        inMemoryData.put(item.getId(), item);
    }

    @Override
    public void delete(@NotNull Long id) {
        inMemoryData.remove(id);
    }

    @Override
    public void update(@NotNull Meal item) {
        inMemoryData.computeIfPresent(item.getId(), (key, value) -> item);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(inMemoryData.values());
    }

    @Override
    public @Nullable Meal getById(@NotNull Long id) {
        return inMemoryData.get(id);
    }
}