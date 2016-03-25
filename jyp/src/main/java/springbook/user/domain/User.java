package springbook.user.domain;

import org.springframework.beans.BeansException;

import jyp.springframework.beans.factory.*;
import jyp.springframework.beans.factory.config.BeanPostProcessor;
import jyp.springframework.context.ResourceLoaderAware;
import jyp.springframework.core.io.ResourceLoader;
import lombok.ToString;

@ToString
public class User implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware,
        ResourceLoaderAware, BeanPostProcessor {

    String id;
    String name;
    String password;
    String email;

    Level level;

    int login;
    int recommend;

    Age age;

    public User() {
    }

    public User(Age age) {
        this.age = age;
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    public User(String id, String name, String password, String email, Level level, int login, int recommend,
            Age age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.age = age;
    }

    public User(String id, String name, String password, Level level, int login, int recommend) {
        this(id, name, password, level, login, recommend, null);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public void setBeanName(String name) {
    }

    @Override
    public void setBeanFactory(BeanFactory var1) throws BeansException {
    }

    @Override
    public void setResourceLoader(ResourceLoader var1) {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name)
            throws jyp.springframework.beans.BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name)
            throws jyp.springframework.beans.BeansException {
        return bean;
    }

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalArgumentException(this.level + "은 업그레이드가 불가능합니다");
        } else {
            this.setLevel(nextLevel);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }
}
