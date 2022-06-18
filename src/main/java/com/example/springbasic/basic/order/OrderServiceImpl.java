package com.example.springbasic.basic.order;

import com.example.springbasic.basic.annotation.MainDiscountPolicy;
import com.example.springbasic.basic.discount.DiscountPolicy;
import com.example.springbasic.basic.member.Member;
import com.example.springbasic.basic.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    /**
     * orderServiceImpl이 fixDiscountPolicy, RateDiscountPolicy 구체화에 의존하고 있음(클라이언트 변경이 필요함)
     * dip 위반
     * ocp 위반
     * => dip를 위반하지 않도록 인터페이스만 의존하도록
     */
    private final DiscountPolicy discountPolicy;

    /**
     * 생성자가 하나일 경우에는 @Autowired 사용
     */
    /**
     * 빈을 조회할 때는 type으로 조회
     * 같은 타입이 2개 이상일 경우 필드 명 매칭이 가능함 (rateDiscountPolicy)
     */
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy rateDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = rateDiscountPolicy;
    }

    /**
     * 필드 주입, 수정자 주입
     */
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discount = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discount);
    }

    // test
    public MemberRepository getMemberRepositoryTest() {
        return memberRepository;
    }

}
