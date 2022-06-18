package com.example.springbasic.basic.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void protoTypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);
        ProtoTypeBean bean1 = ac.getBean(ProtoTypeBean.class);
        ProtoTypeBean bean2 = ac.getBean(ProtoTypeBean.class);
        Assertions.assertThat(bean1).isNotSameAs(bean2);
    }

    @Scope("prototype")
    static class ProtoTypeBean {
        @PostConstruct
        public void init() {

        }

        @PreDestroy
        public void destroy() {

        }
    }

}
