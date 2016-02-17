package org.springframework.context;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public interface HierarchicalMessageSource extends MessageSource {

    void setParentMessageSource(MessageSource parent);

    MessageSource getParentMessageSource();
}
