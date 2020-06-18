package com.example.elastalert.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.elastalert.model.Event;
import com.example.elastalert.service.KafkaMessageService;

@Service
public class KafkaMesssageServiceImpl implements KafkaMessageService{

	private Logger log=LoggerFactory.getLogger(KafkaMesssageServiceImpl.class);

	
	@Override
	public boolean publishEvent(Event event) {
		log.debug("Publish evevent {}",event);
		return false;
	}

}
