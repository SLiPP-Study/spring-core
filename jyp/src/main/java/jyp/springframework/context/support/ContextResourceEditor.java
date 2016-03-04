package jyp.springframework.context.support;

import jyp.springframework.context.ApplicationContext;
import jyp.springframework.core.io.ResourceEditor;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 15.
 */
public class ContextResourceEditor extends ResourceEditor {

    private final ApplicationContext applicationContext;

    public ContextResourceEditor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        String resolveedPath = resolvePath(text);
        setValue(this.applicationContext.getResource(resolveedPath));
    }
}
