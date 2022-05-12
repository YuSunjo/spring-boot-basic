package com.example.springbasic.web;

import com.example.springbasic.main.MyLogger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    public LogDemoService(ObjectProvider<MyLogger> myLoggerObjectProvider) {
        this.myLoggerObjectProvider = myLoggerObjectProvider;
    }

    public void log() {
        MyLogger myLogger = myLoggerObjectProvider.getObject();
        myLogger.log("service");
    }

}
