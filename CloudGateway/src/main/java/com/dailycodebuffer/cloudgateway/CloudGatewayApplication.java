package com.dailycodebuffer.cloudgateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

    @Bean
    KeyResolver userKeySolver(){
        return exchange -> Mono.just("userKey");
    }
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(ReactiveLoadBalancer.Factory factory){
        return factor -> factor.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build()
        );
    }

}
