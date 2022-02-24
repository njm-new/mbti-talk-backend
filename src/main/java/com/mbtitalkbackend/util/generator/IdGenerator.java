package com.mbtitalkbackend.util.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
    public String generate(ServiceType serviceType) {
        return serviceType.name() + '-' + UUID.randomUUID();
    }
}
