package com.example.elastalert.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.elastalert.model.Event;
import com.example.elastalert.service.RedisCacheService;

@Service
public class RedisCacheServiceImpl implements RedisCacheService{

	private Logger log=LoggerFactory.getLogger(RedisCacheServiceImpl.class);

	
	@Override
	public void save(Event event) {
		log.debug("save event {}",event);
		
	}

	@Override
	public Event findByName(String name) {
		log.debug("findByName {} ",name);
		return null;
	}

	@Override
	public List<Event> findByType(String type) {
		log.debug("findByType {}",type);
		return null;
	}
	
	

}
