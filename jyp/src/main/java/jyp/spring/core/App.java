package jyp.spring.core;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 22.
 */
public class App {

    public static void main(String args[]) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        Vehicle obj = (Vehicle) context.getBean("car");
        obj.drive();

        /*Tyre tyre = (Tyre) context.getBean("tyre");
        System.out.println(tyre);*/
    }
}
