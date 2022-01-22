package com.mbtitalkbackend.configuration;

import com.mbtitalkbackend.resolver.MemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ArgumentResolverConfig extends WebMvcConfigurationSupport {
    private final MemberArgumentResolver memberArgumentResolver;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberArgumentResolver);
    }
}
