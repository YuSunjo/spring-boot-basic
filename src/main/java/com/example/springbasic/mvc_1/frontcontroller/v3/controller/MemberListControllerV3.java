package com.example.springbasic.mvc_1.frontcontroller.v3.controller;

import com.example.springbasic.mvc_1.frontcontroller.ModelView;
import com.example.springbasic.mvc_1.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("members");
    }

}
