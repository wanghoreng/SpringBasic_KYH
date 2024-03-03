package hello.core;

import hello.core.member.*;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

        /* 스프링 컨테이너에 설정정보를 등록하여 설계하는 방식*/
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        /* 의존관계를 주입받아서, 인터페이스에만 의존하는 설계 */
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

//        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L,"memberA", Grade.VIP);

        memberService.join(member); // 회원가입 로직

        Member findMember = memberService.findMember(1L); // 회원ID 로 멤버 찾기

        // 값이 잘 들어갔는지 확인
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());

        /*
        new member = memberA
        find Member = memberA
        */
    }
}
