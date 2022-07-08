package com.example.springbasic.mvc_1.frontcontroller.v2;

import com.example.springbasic.mvc_1.frontcontroller.MyView;
import com.example.springbasic.mvc_1.frontcontroller.v1.ControllerV1;
import com.example.springbasic.mvc_1.frontcontroller.v1.controller.MemberFormControllerV1;
import com.example.springbasic.mvc_1.frontcontroller.v1.controller.MemberListControllerV1;
import com.example.springbasic.mvc_1.frontcontroller.v1.controller.MemberSaveControllerV1;
import com.example.springbasic.mvc_1.frontcontroller.v2.controller.MemberFormControllerV2;
import com.example.springbasic.mvc_1.frontcontroller.v2.controller.MemberListControllerV2;
import com.example.springbasic.mvc_1.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerV2Map = new HashMap<>();

    public FrontControllerServletV2() {
        controllerV2Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerV2Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerV2Map.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV2 controller = controllerV2Map.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_FOUND);
            return;
        }
        MyView process = controller.process(request, response);
        process.render(request, response);
    }

}
