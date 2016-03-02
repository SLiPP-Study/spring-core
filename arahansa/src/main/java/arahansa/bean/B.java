package arahansa.bean;

/**
 * Created by arahansa on 2016-03-02.
 */
public class B {

    public String name = "B";
    public Integer age = 1;


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "B {" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
