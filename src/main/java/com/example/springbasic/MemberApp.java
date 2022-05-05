package com.example.springbasic;

import com.example.springbasic.member.*;

public class MemberApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "member1", Grade.VIP);
        memberService.join(member);

        memberService.findMember(1L);
    }

}
