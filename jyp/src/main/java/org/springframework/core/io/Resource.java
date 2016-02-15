package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface Resource extends InputStreamSource {

    boolean exists();

    boolean isOpen();

    URL getURL() throws IOException;

    File getFile() throws IOException;

    String getDescription();
}
