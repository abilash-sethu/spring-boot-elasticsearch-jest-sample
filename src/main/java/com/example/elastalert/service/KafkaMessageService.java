package com.example.elastalert.service;

import com.example.elastalert.model.Event;

public interface KafkaMessageService {

	
	public boolean publishEvent(Event event);
}
