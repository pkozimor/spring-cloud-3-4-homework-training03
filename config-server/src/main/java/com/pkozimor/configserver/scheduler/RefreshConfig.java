package com.pkozimor.configserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Component responsible for periodically refreshing configuration of target services.
 * It uses Service Discovery to locate instances and triggers their refresh endpoints.
 */
@Component
public class RefreshConfig {
    private static final Logger log = LoggerFactory.getLogger(RefreshConfig.class);
    private static final String TARGET_SERVICE = "ms1";
    private static final String REFRESH_ENDPOINT = "/actuator/refresh";
    private static final String EMPTY_JSON_BODY = "{}";

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public RefreshConfig(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${config.refresh.fixedRate}")
    public void refreshConfig() {
        List<ServiceInstance> instances = discoveryClient.getInstances(TARGET_SERVICE);
        if (instances.isEmpty()) {
            log.warn("No instances found for service: {}", TARGET_SERVICE);
            return;
        }

        instances.forEach(this::refreshInstance);
    }

    private void refreshInstance(ServiceInstance instance) {
        String url = instance.getUri() + REFRESH_ENDPOINT;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(EMPTY_JSON_BODY, headers);

            restTemplate.postForObject(url, entity, String.class);
            log.info("Successfully refreshed configuration for instance: {}", url);
        } catch (Exception e) {
            log.error("Failed to refresh configuration for instance: {}. Error: {}", url, e.getMessage(), e);
        }
    }
}
