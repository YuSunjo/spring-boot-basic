package com.example.springbasic.mvc_1.frontcontroller.v3.controller;

import com.example.springbasic.mvc_1.frontcontroller.ModelView;
import com.example.springbasic.mvc_1.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        return new ModelView("save-result");
    }

}
