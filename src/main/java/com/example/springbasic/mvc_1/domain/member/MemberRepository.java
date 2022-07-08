package com.example.springbasic.mvc_1.domain.member;

import java.util.HashMap;
import java.util.Map;

class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    public MemberRepository() {
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

}
