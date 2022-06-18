package com.example.springbasic.basic;

import com.example.springbasic.basic.discount.RateDiscountPolicy;
import com.example.springbasic.basic.member.MemberService;
import com.example.springbasic.basic.member.MemberServiceImpl;
import com.example.springbasic.basic.member.MemoryMemberRepository;
import com.example.springbasic.basic.order.OrderService;
import com.example.springbasic.basic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /**
     * 구현 객체를 생성해줌
     * 생성자를 통해 주입
     */
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), getDiscountPolicy());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public RateDiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy();
    }

}
