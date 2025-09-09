package com.ibrahim.drop_shop.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {

    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }
}
