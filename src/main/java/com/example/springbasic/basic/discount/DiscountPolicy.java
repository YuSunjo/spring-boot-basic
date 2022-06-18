package com.example.springbasic.basic.discount;

import com.example.springbasic.basic.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);

}
