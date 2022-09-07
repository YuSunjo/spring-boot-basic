package com.example.springbasic.mvc_2.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/upload")
    public String saveFileV2(@RequestParam String itemName, @RequestParam MultipartFile file, HttpServletRequest request) throws ServletException, IOException {
        String fullPath = fileDir + file.getOriginalFilename();
        file.transferTo(new File(fullPath));
        return "upload-form";
    }

}
