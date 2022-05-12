package com.example.springbasic.web;

import com.example.springbasic.main.MyLogger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    public LogDemoController(LogDemoService logDemoService, ObjectProvider<MyLogger> myLoggerObjectProvider) {
        this.logDemoService = logDemoService;
        this.myLoggerObjectProvider = myLoggerObjectProvider;
    }

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        MyLogger myLogger = myLoggerObjectProvider.getObject();
        myLogger.setRequestUrl(requestUrl);
        myLogger.log("controller test");
        logDemoService.log();
        return "gogo";
    }

}
