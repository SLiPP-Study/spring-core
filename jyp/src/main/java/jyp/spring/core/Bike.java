package jyp.spring.core;

import org.springframework.stereotype.Component;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 22.
 */

@Component
public class Bike implements Vehicle{
    public void drive() {
        System.out.println("Bike Drive!!");
    }
}
