package org.springframework.core.io;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
