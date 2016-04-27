package org.springframework.context;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * Created by arahansa on 2016-03-01.
 */
public interface ConfigurableApplicationContext {

    void setParent(ApplicationContext parent);

    /**
     * BeanFactoryPostProcessor �߰��Ѵ�.
     * @param beanFactoryPostProcessor the factory processor to register
     */
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);

    /**
     *
     * ���� ǥ�� �ε� Ȥ�� ��������. ���⼭ ����ǥ���̶����� XML, properties File, ������ �����ͺ��̽� ��Ű�� ����� ����.
     * @���� : ApplicationContextException ; ������ �ε�ȵ� ���
     * @���� : BeansException ; ���丮�� �ʱ�ȭ �ȵ� ��
     */
    void refresh() throws BeansException;


    ConfigurableListableBeanFactory getBeanFactory();

    void close() throws ApplicationContextException;
}
