package jyp.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 22.
 */

@Component
public class Car implements Vehicle {

    @Autowired
    private Tyre tyre;

    public Tyre getTyre() {
        return tyre;
    }

    public void setTyre(Tyre tyre) {
        this.tyre = tyre;
    }

    public void drive() {
        System.out.println("car " + tyre);
    }
}
