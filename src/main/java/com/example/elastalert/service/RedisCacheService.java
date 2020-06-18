package com.example.elastalert.service;

import java.util.List;

import com.example.elastalert.model.Event;

public interface RedisCacheService {

	public void save(Event event);
	
	public Event findByName(String  name);

	public List<Event> findByType(String type);
}
