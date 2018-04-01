package com.pelicanus.insight.model;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alkempl on 4/1/18.
 */

public class DataHolder {
    private static volatile DataHolder instance;
    private Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();

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

    public void save(String id, Object object) {
        data.put(id, new WeakReference<Object>(object));
    }

    public Object retrieve(String id) {
        WeakReference<Object> objectWeakReference = data.get(id);
        return objectWeakReference.get();
    }
}
