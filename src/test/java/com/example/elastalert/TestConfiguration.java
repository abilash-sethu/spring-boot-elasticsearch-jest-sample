package com.example.elastalert;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.example.elastalert.service.KafkaMessageService;
import com.example.elastalert.service.RedisCacheService;

@Profile(value = {"test"})
@Configuration
public class TestConfiguration {
	@Bean
	@Primary
	public RedisCacheService redisCacheService() {
		return Mockito.mock(RedisCacheService.class);
	}
	
	@Bean
	@Primary
	public KafkaMessageService kafkaMessageService() {
		return Mockito.mock(KafkaMessageService.class);
	}
}
