package com.ibrahim.drop_shop.config;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ShopConfig {


    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }


}
