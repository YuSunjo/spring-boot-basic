package com.example.springbasic.basic.autowired;

import com.example.springbasic.basic.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired(required = false)
        public void setNoBean(Member member) {

        }

        @Autowired
        public void setNoBean2(@Nullable Member member) {

        }

        @Autowired
        public void setNoBean3(Optional<Member> member) {

        }

    }

}
