package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    /**
     * 싱글톤 빈과 함께 프로토타입 빈의 스코프가 유지되는 문제
     * */

    /*
    @Scope("singleton") // 싱글톤으로 기본등록되지만, 가시성을 위해 적어둠
    static class ClientBean {

        // 프로토타입 빈으로 생성시점에 주입된 빈이 싱글톤 빈으로 유지되는 ClientBean 과 같이 스코프가 유지된다.
        // 원래 프로토타입의 기능을 사용하기 위해서라면, 계속해서 새로운 프로토타입 빈이 만들어지는 것을 원할 텐데,
        // 지금은 싱글톤 빈과 함께 쓰이게 되면서 프로토타입 빈의 기능을 사용하지 못하고 있다.
        private PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
    */

    /**
     * 무식하게 직접 스프링 컨테이너를 주입받아서 요청받는 방식
     * => 스프링의 애플리케이션 컨텍스트 전체를 주입받게 되면, 스프링 컨테이너에 종속적인 코드가 되고, 단위 테스트도 어려워진다.
     * */

    /*
    @Scope("singleton")
    static class ClientBean {

        @Autowired
        private ApplicationContext ac;


        public int logic() {
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
    */


    /**
     * ObjectProvider 로 스프링컨텍스트 전체를 조회하지 않고 대신 조회해주는 DL 기능을 제공한다.
     * -> ObjectProvider 는 ObjectFactory 에서 상속받아 만들어졌다.
     * */
    /*
    @Scope("singleton")
    static class ClientBean {

        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
    */

    /**
     * JSR-330 Provider 라이브러리를 사용 : 스프링에 의존적이지 않다.
     * */

    @Scope("singleton")
    static class ClientBean {

        private Provider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count ++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destory() {
            System.out.println("PrototypeBean.destory");
        }

    }
}
