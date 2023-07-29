package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContainerImp implements Container{
    private final Map<Class<?>, Object> dependencies = new HashMap<>();
    public ContainerImp(String... packageNames) {
        Set<Class<?>> classes = new HashSet<>();
        for (String packageName : packageNames) {
            classes.addAll(ClassScanner.find(packageName));
        }
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Dependency.class)) {
                try {
                    dependencies.put(clazz, clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @Override
    public void register(Class<?> type) {
        try {
            dependencies.put(type, type.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T resolve(Class<T> type) {
        return type.cast(dependencies.get(type));
    }
}
