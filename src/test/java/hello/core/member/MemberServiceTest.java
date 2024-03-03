package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

//    MemberService memberService = new MemberServiceImpl();
    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        // given - member의 정보가 주어지고
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when - 해당 member 가 회원가입을 하고, 회원 ID 로 조회된 값을 넣어줬을 때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then - member와 findmember 가 같은지 비교해주는 테스트
        Assertions.assertThat(member).isEqualTo(findMember);
    }

}
