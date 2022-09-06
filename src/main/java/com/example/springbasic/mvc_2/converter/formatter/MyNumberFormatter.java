package com.example.springbasic.mvc_2.converter.formatter;

import org.springframework.format.Formatter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MyNumberFormatter implements Formatter<Number> {

    // 스프링에서 기본으로 지원해주는 포매터
//    @NumberFormat(pattern = "###,###")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss)

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        // "1,000" -> 1000
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        return NumberFormat.getInstance(locale).format(object);
    }

}
