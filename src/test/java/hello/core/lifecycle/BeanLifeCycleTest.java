package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        //NetworkClient client = ac.getBean(NetworkClient.class);
        //NetworkClient2 client2 = ac.getBean(NetworkClient2.class);
        NetworkClient3 client3 = ac.getBean(NetworkClient3.class);
        ac.close(); // 스프링 컨테이너에서 close 메소드를 사용하기 위해서는  AnnotationConfigApplicationContext 또는 ConfigurableApplicationContext 를 사용해야 한다.

    }



    @Configuration
    static class LifeCycleConfig {

        /**
         * 스프링 전용 인터페이스 사용한 빈 설정
         * */
        /*
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
        */

        /**
         * @Bean 설정에서 초기화, 소멸 메소드 지정하는 방법
         * */
        //destoryMethod 에는 추론 기능이 있기 때문에, 따로 소멸 메소드를 지정해주지 않아도
        //자동으로 소멸 메소드를 호출해준다.
        // (대부분 외부 라이브러리의 소멸 메소드명이 close, shutdown 이여서 소멸 메소드명이 둘 중 하나일 때 추론기능이 사용된다)
        /*
        @Bean(initMethod = "init", destroyMethod = "close") // 설정 정보에 초기화, 소멸 메소드를 직접 지정할 수 있다.
        public NetworkClient2 networkClient() {
            NetworkClient2 networkClient2 = new NetworkClient2();
            networkClient2.setUrl("http://hello-spring.dev");
            return networkClient2;
        }
        */

        @Bean
        public NetworkClient3 networkClient() {
            NetworkClient3 networkClient3 = new NetworkClient3();
            networkClient3.setUrl("http://hello-spring.dev");
            return networkClient3;
        }
    }
}
