package org.jarvis.java8.prototype.shape;

import java.util.HashMap;

/**
 * author:Lovel  date:2020/5/18
 */
public class ProtoTypeManager {
    private HashMap<String, Shape> ht = new HashMap<String, Shape>();

    public ProtoTypeManager() {
        ht.put("Circle", new Circle());
        ht.put("Square", new Square());
    }

    public void addshape(String key, Shape obj) {
        ht.put(key, obj);
    }

    public Shape getShape(String key) {
        return ht.get(key);
    }
}
