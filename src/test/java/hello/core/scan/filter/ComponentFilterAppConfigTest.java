package hello.core.scan.filter;

import hello.core.filter.MyExcludeComponent;
import hello.core.filter.MyIncludeComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean(BeanA.class);

        Assertions.assertThat(beanA).isNotNull();

        // beanB 생성 시 예외 발생한다면 테스트 성공
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,() -> ac.getBean("beanB", BeanB.class));
    }


    @Configuration
    @ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
                    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class))
    static class ComponentFilterAppConfig {

    }
}
