package net.woniper.spring.core.beans.supports;

import net.woniper.obj.Account;
import net.woniper.spring.core.beans.config.BeanDefinition;

import java.io.IOException;

/**
 * Created by woniper on 2016. 2. 28..
 */
public class BeanObjectReader {
    private BeanObjectRegistry beanObjectRegistry;

    public BeanObjectReader(BeanObjectRegistry beanObjectRegistry) {
        this.beanObjectRegistry = beanObjectRegistry;
    }

    public void loadBeanObject(BeanDefinition beanDefinition) {
        try {
            beanObjectRegistry.registerBeanObject(beanDefinition.getBeanName(), getNewInstance(beanDefinition.getClassType()));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void loadBeanObjects(String basePackage) throws IOException {
        // 테스트를 위한 코드
        // 추후 package scan 코드 추가 예정

        beanObjectRegistry.registerBeanObject("account", new Account());
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        String path = basePackage.replace(".", "/");
//        Enumeration<URL> resources = classLoader.getResources(path);
//        List<File> classFiles = new ArrayList<>();
//
//        while (resources.hasMoreElements()) {
//            URL url = resources.nextElement();
//
//            File classFile = new File(url.getFile());
//            if(classFile.exists()) {
//                if(classFile.isDirectory()) {
//
//                } else if(classFile.getName().endsWith(".class")) {
//                    Class.forName(basePackage + "." + classFile.getName())
//                    beanObjectRegistry.registerBeanObject();
//                }
//            }
//        }
    }

    private Object getNewInstance(Class<?> clz) throws IllegalAccessException, InstantiationException {
        return clz.newInstance();
    }
}
