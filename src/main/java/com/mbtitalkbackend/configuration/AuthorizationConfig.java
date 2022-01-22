package com.mbtitalkbackend.configuration;

import com.mbtitalkbackend.util.authrization.AccessToken;
import com.mbtitalkbackend.util.authrization.AuthorizationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationConfig {
    @Bean
    public AccessToken accessToken() {
        return new AccessToken();
    }

    @Bean
    public AuthorizationAspect authorizationAspect() {
        return new AuthorizationAspect();
    }
}
