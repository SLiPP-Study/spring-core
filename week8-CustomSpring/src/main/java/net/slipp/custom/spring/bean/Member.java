package net.slipp.custom.spring.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woniper on 2016. 5. 5..
 */
public class Member {

    private List<String> springMembers = new ArrayList<>();

    public Member() {
        springMembers.add("이경원");
        springMembers.add("홍광필");
    }

    public void print() {
        System.out.println(springMembers.toString());
    }

}
