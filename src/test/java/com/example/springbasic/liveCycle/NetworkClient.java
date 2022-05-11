package com.example.springbasic.liveCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

    /**
     * spring 전용 인터페이스
     * 초기화 소멸 메서드의 이름을 변경할 수 없다.
     * 내가 코드를 고칠 수 없는 외부 라이브러리에 적용 불가능
     */

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect" + url);
    }

    public void call(String message) {
        System.out.println("message = " + message);
    }

    // 종료시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    @Override
    public void destroy() throws Exception {
        disconnect();
    }

    // 초기화 연결 메세지
    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메세지");
    }
}
