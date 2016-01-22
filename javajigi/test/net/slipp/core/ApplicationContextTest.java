package net.slipp.core;

import net.slipp.ApplicationContext;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void init() {
        ApplicationContext ac = new DefaultApplicationContext("net.slipp");
    }
}
