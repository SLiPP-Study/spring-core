package jyp.springframework.beans.factory.xml;

import jyp.springframework.core.io.ClassPathResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.IOException;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public class BeansDtdResolver implements EntityResolver {
    private static final String DTD_NAME = "spring-beans";
    private static final String SEARCH_PACKAGE = "/org/springframework/beans/factory/xml/";
    protected final Log logger = LogFactory.getLog(this.getClass());

    public BeansDtdResolver() {
    }

    public InputSource resolveEntity(String publicId, String systemId) throws IOException {
        this.logger.debug("Trying to resolve XML entity with public ID [" + publicId + "] and system ID [" + systemId + "]");
        if(systemId != null && systemId.indexOf("spring-beans") > systemId.lastIndexOf("/")) {
            String dtdFile = systemId.substring(systemId.indexOf("spring-beans"));
            this.logger.debug("Trying to locate [" + dtdFile + "] under [" + "/org/springframework/beans/factory/xml/" + "]");

            try {
                ClassPathResource ex = new ClassPathResource("/org/springframework/beans/factory/xml/" + dtdFile, this.getClass());
                InputSource source = new InputSource(ex.getInputStream());
                source.setPublicId(publicId);
                source.setSystemId(systemId);
                this.logger.debug("Found beans DTD [" + systemId + "] in classpath");
                return source;
            } catch (IOException var6) {
                this.logger.debug("Could not resolve beans DTD [" + systemId + "]: not found in classpath", var6);
            }
        }

        return null;
    }
}
