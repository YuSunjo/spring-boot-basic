package com.example.springbasic.basic.autowired;

import com.example.springbasic.basic.AutoAppConfig;
import com.example.springbasic.basic.discount.DiscountPolicy;
import com.example.springbasic.basic.member.Grade;
import com.example.springbasic.basic.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "user1", Grade.VIP);
        int fixDiscountPolicy = discountService.discount(member, 10000, "fixDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscountPolicy).isEqualTo(1000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountService> discountServiceList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountService> discountServiceList) {
            this.policyMap = policyMap;
            this.discountServiceList = discountServiceList;
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }

    }

}
