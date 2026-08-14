package io.github.Mishaa105.price_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Executor
{
    @Bean
    public ExecutorService virtualThreadExecutor()
    {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
