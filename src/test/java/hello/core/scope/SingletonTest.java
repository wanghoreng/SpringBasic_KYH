package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {

    @Test
    public void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        // 스프링 컨테이너가 생성될 때 싱글톤 빈도 생성되고, 초기화 메서드가 실행된다.
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);
        // isSameAs 는 '==' 과 같은 역할을 하는 메서드다.
        Assertions.assertThat(singletonBean1).isSameAs(singletonBean2);
        // 싱글톤 타입은 같은 인스턴스 빈을 반환한다.

        // 싱글톤 타입은 소멸 되는 것까지 관리하는 것을 알 수 있다.
        ac.close();

    }

    @Scope("singleton")
    static class SingletonBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destory() {
            System.out.println("SingletonBean.destory");
        }

    }
}
