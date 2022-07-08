package com.example.springbasic.mvc_1.frontcontroller.v2.controller;

import com.example.springbasic.mvc_1.frontcontroller.MyView;
import com.example.springbasic.mvc_1.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberListControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return new MyView("list");
    }

}
