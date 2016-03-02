package analyze.context;


import analyze.beans.BeansException;
import analyze.beans.factory.config.BeanFactoryPostProcessor;
import analyze.beans.factory.config.ConfigurableListableBeanFactory;

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
