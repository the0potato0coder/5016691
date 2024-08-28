package com.bookstoreapi.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMetricsConfig {

    @Bean
    public MeterBinder customMetrics() {
        return (MeterRegistry registry) -> {
            Counter.builder("custom.book.created")
                    .description("Number of books created")
                    .register(registry);
        };
    }
}