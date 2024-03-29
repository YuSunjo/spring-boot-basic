package com.example.springbasic.DB_1.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private String memberId;
    private int money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }

}
