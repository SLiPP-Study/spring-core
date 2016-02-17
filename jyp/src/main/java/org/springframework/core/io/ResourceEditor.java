package org.springframework.core.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyEditorSupport;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public class ResourceEditor extends PropertyEditorSupport {

    protected static final Log logger = LogFactory.getLog(ResourceEditor.class);

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    public void setAsText(String text) {
        setValue(getResourceLoader().getResource(resolvePath(text)));
    }

    protected String resolvePath(String path) {
        int startIndex = path.indexOf(PLACEHOLDER_PREFIX);
        if (startIndex != -1) {
            int endIndex = path.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
            if (endIndex != -1) {
                String placeHolder = path.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                String propVal = System.getProperty(placeHolder);
                if (propVal != null) {
                    return path.substring(0, startIndex) + propVal + path.substring(endIndex+1);
                } else {
                    logger.warn("Could not resolve placeholder '" + placeHolder + "' in file path [" + path + "] as system property");
                }
            }
        }
        return path;
    }

    private ResourceLoader getResourceLoader() {
        return new DefaultResourceLoader();
    }
}
