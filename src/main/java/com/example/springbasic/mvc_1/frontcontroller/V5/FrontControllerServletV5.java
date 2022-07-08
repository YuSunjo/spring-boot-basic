package com.example.springbasic.mvc_1.frontcontroller.V5;

import com.example.springbasic.mvc_1.frontcontroller.ModelView;
import com.example.springbasic.mvc_1.frontcontroller.MyView;
import com.example.springbasic.mvc_1.frontcontroller.V5.adaptor.ControllerV3HandlerAdaptor;
import com.example.springbasic.mvc_1.frontcontroller.V5.adaptor.ControllerV4HandlerAdaptor;
import com.example.springbasic.mvc_1.frontcontroller.v3.controller.MemberFormControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v3.controller.MemberListControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.example.springbasic.mvc_1.frontcontroller.v4.ControllerV4;
import com.example.springbasic.mvc_1.frontcontroller.v4.controller.MemberFormControllerV4;
import com.example.springbasic.mvc_1.frontcontroller.v4.controller.MemberListControllerV4;
import com.example.springbasic.mvc_1.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdaptors();
    }

    private boolean initHandlerAdaptors() {
        handlerAdapters.add(new ControllerV3HandlerAdaptor());
        return handlerAdapters.add(new ControllerV4HandlerAdaptor());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if (handler == null) {
            // 응답
        }
        MyHandlerAdapter handlerAdaptor = getHandlerAdaptor(handler);
        ModelView modelView = handlerAdaptor.handle(req, resp, handler);

        String viewName = modelView.getViewName();
        MyView myView = viewResolver(viewName);
        myView.render(modelView.getModel(), req, resp);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB_INF/view/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdaptor(Object handler) {
        for (MyHandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new IllegalArgumentException("handler adaptor");
    }

    private Object getHandler(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

}
