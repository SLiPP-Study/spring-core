package jyp.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
}
