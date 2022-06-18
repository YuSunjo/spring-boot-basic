package com.example.springbasic.basic.web;

import com.example.springbasic.basic.main.MyLogger;
import org.springframework.stereotype.Service;

@Service
public class LogDemoService {

    private final MyLogger myLogger;

    public LogDemoService(MyLogger myLogger) {
        this.myLogger = myLogger;
    }

    public void log() {
        myLogger.log("service");
    }

}
