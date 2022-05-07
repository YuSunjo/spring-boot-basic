package com.example.springbasic.singleton;

import com.example.springbasic.AppConfig;
import com.example.springbasic.member.MemberRepository;
import com.example.springbasic.member.MemberServiceImpl;
import com.example.springbasic.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    /**
     * Appconfig 에서 new MemoryMemberRepository 부르고
     * order에서도 new MemberMemberRepository 부르는데 싱글톤이 깨지는 것인가??
     * 결론: 싱글톤이 유지가 됨
     */
    @Test
    @DisplayName("appconfi")
    void configureTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepositoryTest1 = memberService.getMemberRepositoryTest();
        MemberRepository memberRepositoryTest2 = orderService.getMemberRepositoryTest();
        assertThat(memberRepositoryTest1).isSameAs(memberRepositoryTest2);
    }

    /**
     * 스프링이 CGLIB라는 바이트 코드 조작 라이브러리를 사용해서
     * AppConfig를 상속받은 임의의 다른 클래스를 만들고 그 다른 클래스를 스프링 빈으로 등록한 것
     */
    @Test
    void configureDeepTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
    }

}
