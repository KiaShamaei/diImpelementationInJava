package org.example;

public interface Container {
    void register(Class<?> type);
    <T> T resolve(Class<T> type);
}
