package com.example.springbasic.mvc_1.frontcontroller.v3;

import com.example.springbasic.mvc_1.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);

}
