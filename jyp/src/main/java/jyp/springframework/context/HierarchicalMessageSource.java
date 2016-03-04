package jyp.springframework.context;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public interface HierarchicalMessageSource extends MessageSource {

    void setParentMessageSource(MessageSource parent);

    MessageSource getParentMessageSource();
}
