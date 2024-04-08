package com.learn.asyncapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@SpringBootApplication
@EnableAsync
public class AsyncApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncApiApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Lookup-");
		executor.initialize();
		return executor;
	}
}
