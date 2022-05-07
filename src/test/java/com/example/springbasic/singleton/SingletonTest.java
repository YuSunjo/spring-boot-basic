package com.example.springbasic.singleton;

import com.example.springbasic.AppConfig;
import com.example.springbasic.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    /**
     * 요청이 들어올 때마다 객체를 생성해주는 것은 너무 비효율적
     */

    /**
     * 싱글톤
     * 인스턴스가 1개만 생성되는 것을 보장
     */
    @Test
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();

        MemberService memberService2 = appConfig.memberService();

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    @Test
    void singletonServiceTest() {
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext();
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        assertThat(memberService1).isSameAs(memberService2);
    }

}
