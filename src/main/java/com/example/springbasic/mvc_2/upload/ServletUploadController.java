package com.example.springbasic.mvc_2.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Controller
@Slf4j
@RequestMapping("/servlet/v1")
public class ServletUploadController {

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("req = {}", request);

        String itemName = request.getParameter("itemName");

        Collection<Part> parts = request.getParts();
        System.out.println("parts = " + parts);

        return "upload-form";
    }

}
