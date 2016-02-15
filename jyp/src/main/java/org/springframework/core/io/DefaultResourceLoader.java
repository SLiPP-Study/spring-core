package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public class DefaultResourceLoader implements ResourceLoader {

    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException ex) {
                return getResourceByPath(location);
            }
        }
    }

    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);
    }
}
