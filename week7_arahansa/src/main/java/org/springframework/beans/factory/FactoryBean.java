package org.springframework.beans.factory;

/**
 * Created by arahansa on 2016-03-01.
 *
 * 빈팩토리내부에서 사용될 객체들에 의해 구현. 팩토리빈이 될 것임.
 * 만약 빈이 이 인터페이스를 구현하면 이것은 직접전인 빈이 아닌, 팩토리빈으로 사용된다.
 *
 * 이 인터페이스를 구현하면 보통 빈으로 사용될 수 없음.
 * 팩토리빈은 싱글톤이나 프로토타입 빈을 지원하지 않는다.
 */
public interface FactoryBean {

    Object getObject() throws Exception;
}
