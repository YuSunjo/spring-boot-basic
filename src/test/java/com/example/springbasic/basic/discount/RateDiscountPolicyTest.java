package com.example.springbasic.basic.discount;

import com.example.springbasic.basic.discount.RateDiscountPolicy;
import com.example.springbasic.basic.member.Grade;
import com.example.springbasic.basic.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("vip는 10% 할인 적용")
    void vip_o() {
        // given
        Member member = new Member(1L, "vip", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    void vip_x() {
        // given
        Member member = new Member(1L, "basic", Grade.BASIC);

        // when
        int discount = discountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(0);
    }

}