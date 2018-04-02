package com.pelicanus.insight.model;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alkempl on 4/1/18.
 */

public class DataHolder {
    private static volatile DataHolder instance;
    private Map<String, SoftReference<Object>> data = new HashMap<>();

    public DataHolder() {
    }

    public static DataHolder getInstance() {
        if (instance == null) {
            synchronized (DataHolder.class) {
                if (instance == null)
                    instance = new DataHolder();
            }
        }
        return instance;
    }

    public void save(@SuppressWarnings("SameParameterValue") String id, Object object) {
        data.put(id, new SoftReference<>(object));
    }

    public void remove(String id) {
        SoftReference<Object> objectSoftReference = data.get(id);
        objectSoftReference.clear();
        data.remove(objectSoftReference);
    }

    public Object retrieve(@SuppressWarnings("SameParameterValue") String id) {
        SoftReference<Object> objectSoftReference = data.get(id);
        return objectSoftReference.get();
    }
}
