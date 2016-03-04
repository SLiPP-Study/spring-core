package jyp.springframework.context;


import jyp.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.Aware;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public interface ResourceLoaderAware extends Aware {
    void setResourceLoader(ResourceLoader var1);
}
