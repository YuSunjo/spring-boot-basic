package com.example.springbasic.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithProtoTypeTest {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeTest.ProtoTypeBean.class);
        ProtoTypeBean bean1 = ac.getBean(ProtoTypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        ProtoTypeBean bean2 = ac.getBean(ProtoTypeBean.class);
        bean2.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class, ClientBean.class);
        ClientBean bean = ac.getBean(ClientBean.class);
        int count = bean.logic();
        assertThat(count).isEqualTo(1);

        ClientBean bean2 = ac.getBean(ClientBean.class);
        int count2 = bean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    @Scope("singleton")
    static class ClientBean {

        private final ProtoTypeBean protoTypeBean;

        public ClientBean(ProtoTypeBean protoTypeBean) {
            this.protoTypeBean = protoTypeBean;
        }

        public int logic() {
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }

    }

        @Scope("prototype")
    static class ProtoTypeBean {

        private int count;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @PostConstruct
        public void init() {

        }

        @PreDestroy
        public void destroy() {

        }
    }


}
