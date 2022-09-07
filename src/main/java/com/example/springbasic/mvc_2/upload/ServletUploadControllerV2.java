package com.example.springbasic.mvc_2.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Controller
@Slf4j
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/upload")
    public String saveFileV2(HttpServletRequest request) throws ServletException, IOException {
        log.info("req = {}", request);

        String itemName = request.getParameter("itemName");

        Collection<Part> parts = request.getParts();
        System.out.println("parts = " + parts);

        for (Part part : parts) {
            Collection<String> headerNames = part.getHeaderNames();
            String submittedFileName = part.getSubmittedFileName();
            long size = part.getSize();

            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            if(StringUtils.hasText(submittedFileName)) {
                // fileDir + 해줘야함
                part.write(submittedFileName);
            }
        }
        return "upload-form";
    }

}
