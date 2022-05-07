package com.example.springbasic.order;

import com.example.springbasic.discount.DiscountPolicy;
import com.example.springbasic.member.Member;
import com.example.springbasic.member.MemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    /**
     * orderServiceImpl이 fixDiscountPolicy, RateDiscountPolicy 구체화에 의존하고 있음(클라이언트 변경이 필요함)
     * dip 위반
     * ocp 위반
     * => dip를 위반하지 않도록 인터페이스만 의존하도록
     */
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
