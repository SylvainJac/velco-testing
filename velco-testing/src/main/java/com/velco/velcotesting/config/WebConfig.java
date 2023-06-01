package com.velco.velcotesting.config;

import com.velco.velcotesting.converter.StringToFieldConverter;
import com.velco.velcotesting.converter.StringToSortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortConverter());
        registry.addConverter(new StringToFieldConverter());
    }
}
