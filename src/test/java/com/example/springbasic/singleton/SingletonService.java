package com.example.springbasic.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    /**
     * new SingletonService 를 쓰는 것을 막기 위해
     */
    private SingletonService() {
    }

}
