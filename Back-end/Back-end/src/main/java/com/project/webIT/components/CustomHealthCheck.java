package com.project.webIT.components;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component("customHealthCheck")
public class CustomHealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        try{
            String computerName = InetAddress.getLocalHost().getHostName(); //ten cac port xu li request
            return Health.up().withDetail("computerName", computerName).build(); //200
        } catch (Exception e) {
            return Health.down().withDetail("Error", e.getMessage()).build();
        }
    }
}
