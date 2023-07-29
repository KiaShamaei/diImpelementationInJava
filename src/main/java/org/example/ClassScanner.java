package org.example;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {
    public static Set<Class<?>> find(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace('.', '/');
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        classes.addAll(find(packageName + "." + file.getName()));
                    } else if (file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classes;
    }
}
