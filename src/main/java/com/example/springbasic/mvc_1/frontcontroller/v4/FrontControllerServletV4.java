package com.example.springbasic.mvc_1.frontcontroller.v4;

import com.example.springbasic.mvc_1.frontcontroller.ModelView;
import com.example.springbasic.mvc_1.frontcontroller.MyView;
import com.example.springbasic.mvc_1.frontcontroller.v3.ControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v3.controller.MemberFormControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v3.controller.MemberListControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v4.controller.MemberFormControllerV4;
import com.example.springbasic.mvc_1.frontcontroller.v4.controller.MemberListControllerV4;
import com.example.springbasic.mvc_1.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

    public FrontControllerServletV4() {
        controllerV4Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/v2/members/save", new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/v2/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerV4Map.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);
        MyView myView = viewResolver(viewName);
        myView.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB_INF/view/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

}
