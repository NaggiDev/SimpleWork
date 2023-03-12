package com.example.simplework.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ServiceSelfHealth implements HealthIndicator {

    @Override
    public Health health() {
        //        implement your own logic for service self health check
        //        up() for healthy case, down()/outOfService() for otherwise
        return Health.up().withDetail("service_status", "available").build();
    }
}
