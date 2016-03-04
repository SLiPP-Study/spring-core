package jyp.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import jyp.springframework.beans.factory.xml.BeansDtdResolver;
import org.xml.sax.InputSource;

import jyp.springframework.context.ApplicationContext;
import jyp.springframework.core.io.Resource;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public class ResourceEntityResolver extends BeansDtdResolver {

    private final ApplicationContext applicationContext;

    public ResourceEntityResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws IOException {
        InputSource source = super.resolveEntity(publicId, systemId);
        if (source == null && systemId != null) {
            String resourcePath = null;
            try {
                String givenUrl = new URL(systemId).toString();
                String systemRootUrl = new File("").toURL().toString();
                // try relative to resource base if currently in system root
                if (givenUrl.startsWith(systemRootUrl)) {
                    resourcePath = givenUrl.substring(systemRootUrl.length());
                }
            }
            catch (MalformedURLException ex) {
                // no URL -> try relative to resource base
                resourcePath = systemId;
            }
            if (resourcePath != null) {
                logger.debug("Trying to locate entity [" + systemId + "] as application context resource [" + resourcePath + "]");
                Resource resource = this.applicationContext.getResource(resourcePath);
                logger.info("Found entity [" + systemId + "] as application context resource [" + resourcePath + "]");
                source = new InputSource(resource.getInputStream());
                source.setPublicId(publicId);
                source.setSystemId(systemId);
            }
        }
        return source;
    }

}
