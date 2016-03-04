package jyp.springframework.beans.factory.support;

import java.util.LinkedHashMap;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public class ManagedLinkedMap extends LinkedHashMap {
    public ManagedLinkedMap() {
    }

    public ManagedLinkedMap(int initialCapacity) {
        super(initialCapacity);
    }
}
