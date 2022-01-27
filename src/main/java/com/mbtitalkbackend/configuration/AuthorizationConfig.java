package com.mbtitalkbackend.configuration;

import com.mbtitalkbackend.util.authrization.AccessTokenManager;
import com.mbtitalkbackend.util.authrization.AuthorizationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationConfig {
    @Bean
    public AccessTokenManager accessToken() {
        return new AccessTokenManager();
    }

    @Bean
    public AuthorizationAspect authorizationAspect() {
        return new AuthorizationAspect();
    }
}
