package net.slipp.custom.spring.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by woniper on 2016. 5. 1..
 */
public class CustomScope implements Scope {

    private Map<String, Object> beans = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if(!beans.containsKey(name)) {
            return beans.put(name, objectFactory.getObject());
        }
        return beans.get(name);
    }

    @Override
    public Object remove(String name) {
        return beans.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
