package jyp.springframework;

import jyp.springframework.context.support.ClassPathXmlApplicationContext;
import springbook.user.domain.User;

/**
 * @author jinyoung.park89
 * @since 2016. 3. 16.
 */
public class MAIN {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        User user = (User)applicationContext.getBean("user", User.class);
        user.setName("jyp");

        System.out.println(user);

        //applicationContext.close();
    }
}
