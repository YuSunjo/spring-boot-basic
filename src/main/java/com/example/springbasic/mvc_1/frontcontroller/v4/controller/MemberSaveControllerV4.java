package com.example.springbasic.mvc_1.frontcontroller.v4.controller;

import com.example.springbasic.mvc_1.frontcontroller.ModelView;
import com.example.springbasic.mvc_1.frontcontroller.v3.ControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        return "save-form";
    }

}
