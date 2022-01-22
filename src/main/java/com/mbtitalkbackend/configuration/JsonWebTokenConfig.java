package com.mbtitalkbackend.configuration;

import com.mbtitalkbackend.util.jwt.JsonWebToken;
import com.mbtitalkbackend.util.jwt.JwtAspect;
import com.mbtitalkbackend.util.jwt.MemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JsonWebTokenConfig extends WebMvcConfigurationSupport {
    private final MemberArgumentResolver memberArgumentResolver;

    @Bean
    public JsonWebToken jsonWebToken() {
        return new JsonWebToken();
    }

    @Bean
    public JwtAspect jwtAspect() {
        return new JwtAspect();
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberArgumentResolver);
    }
}
