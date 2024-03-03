package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자로 주입되는 빈이 여러개 일 때 주입하고 싶은 빈 이름을 파라미터명으로 명시하는 방법 1
    //   private final DiscountPolicy rateDiscountPolicy;

    // 생성자로 주입되는 빈이 여러개 일 때 주입하고 싶은 빈 이름을 파라미터명으로 명시하는 방법 2
    // @RequiredArgsConstructor 로는 불가능
//    @Qualifier("mainDiscountPolicy")
//    private final DiscountPolicy discountPolicy;

    // 수정자 주입은 객체가 생성된 뒤 주입되는 것이기 때문에, null 이 뜬다.
    // 또한, IDE 에서 인식못하고 오류를 알려주지 못해서 컴파일 할 때 에러를 알게 된다.
   /* @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }*/

    // lombok 추가로 생성자 주석처리
    @Autowired // Autowired 에서는 @Qualifier 로 했을 때 가능하다.
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);


        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
