package com.example.springbasic.basic.order;

import com.example.springbasic.basic.AppConfig;
import com.example.springbasic.basic.member.Grade;
import com.example.springbasic.basic.member.Member;
import com.example.springbasic.basic.member.MemberService;
import com.example.springbasic.basic.order.Order;
import com.example.springbasic.basic.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {


    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void get() {
        // given
        Member member = new Member(1L, "member", Grade.VIP);
        memberService.join(member);

        // when
        Order order = orderService.createOrder(member.getId(), "itemName", 10000);

        // then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
