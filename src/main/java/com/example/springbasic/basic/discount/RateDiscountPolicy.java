package com.example.springbasic.basic.discount;

import com.example.springbasic.basic.annotation.MainDiscountPolicy;
import com.example.springbasic.basic.member.Grade;
import com.example.springbasic.basic.member.Member;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy")
//@Primary
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }

}
