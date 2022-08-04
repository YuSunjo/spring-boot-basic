package com.example.springbasic.DB_1.repository;

import com.example.springbasic.DB_1.domain.Member;

public interface MemberRepository {

    Member save(Member member);
    Member findById(String memberId);
    void update(String memberId, int money);
    void delete(String memberId);

}
