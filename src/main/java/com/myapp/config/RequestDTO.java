package com.myapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestDTO {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
