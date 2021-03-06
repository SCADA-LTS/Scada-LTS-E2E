package org.scadalts.e2e.test.impl.config.auto.registers;

import org.scadalts.e2e.test.core.creators.CreatorObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CreatorRegister {

    private static Map<Class<?>, List<CreatorObject<?,?>>> creators = new ConcurrentHashMap<>();

    public static void addCreators(Class<?> key, List<CreatorObject<?,?>> creator) {
        creators.computeIfAbsent(key, a -> creators.put(a, new LinkedList<>()));
        creators.get(key).addAll(creator);
    }

    public static List<CreatorObject<?,?>> getCreators(Class<?> key) {
        return creators.get(key);
    }
}
