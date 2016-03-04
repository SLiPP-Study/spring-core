package jyp.springframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class ClassPathResource extends AbstractResource {

    private final String path;

    private Class clazz;

    /**
     * Create a new ClassPathResource for ClassLoader usage.
     * A leading slash will be removed, as the ClassLoader
     * resource access methods will not accept it.
     * @param path the absolute path within the classpath
     * @see ClassLoader#getResourceAsStream
     */
    public ClassPathResource(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        this.path = path;
    }

    /**
     * Create a new ClassPathResource for Class usage.
     * The path can be relative to the given class,
     * or absolute within the classpath via a leading slash.
     * @param path relative or absolute path within the classpath
     * @param clazz the class to load resources with
     * @see Class#getResourceAsStream
     */
    public ClassPathResource(String path, Class clazz) {
        this.path = path;
        this.clazz = clazz;
    }

    public InputStream getInputStream() throws IOException {
        InputStream is = null;
        if (this.clazz != null) {
            is = this.clazz.getResourceAsStream(this.path);
        }
        else {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            is = ccl.getResourceAsStream(this.path);
        }
        if (is == null) {
            throw new FileNotFoundException("Could not open " + getDescription());
        }
        return is;
    }

    public URL getURL() throws IOException {
        URL url = null;
        if (this.clazz != null) {
            url = this.clazz.getResource(this.path);
        }
        else {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            url = ccl.getResource(this.path);
        }
        if (url == null) {
            throw new FileNotFoundException(getDescription() + " cannot be resolved to URL " +
                    "because it does not exist");
        }
        return url;
    }

    public File getFile() throws IOException {
        URL url = getURL();
        if (!URL_PROTOCOL_FILE.equals(url.getProtocol())) {
            throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path " +
                    "because it does not reside in the file system: URL=[" + url + "]");
        }
        return new File(URLDecoder.decode(url.getFile()));
    }

    public String getDescription() {
        return "class path resource [" + this.path + "]";
    }

}
