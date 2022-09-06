package com.example.springbasic.mvc_2.converter;

import com.example.springbasic.mvc_2.converter.converter.StringToIntegerConverter;
import com.example.springbasic.mvc_2.converter.converter.StringToIpPortConverter;
import com.example.springbasic.mvc_2.converter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIpPortConverter());
//        registry.addConverter(new StringToIntegerConverter());

        // 포매터
        registry.addFormatter(new MyNumberFormatter());
    }

}
