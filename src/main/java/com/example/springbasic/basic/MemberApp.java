package com.example.springbasic.basic;

import com.example.springbasic.basic.member.Grade;
import com.example.springbasic.basic.member.Member;
import com.example.springbasic.basic.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        ApplicationContext applicationContext= new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "member1", Grade.VIP);
        memberService.join(member);

        memberService.findMember(1L);
    }

}
