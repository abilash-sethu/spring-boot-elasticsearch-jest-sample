package com.example.elastalert.service;

import com.example.elastalert.model.Event;

public interface EventService {

	public Event create(Event event);
	public Event save(Event event);
	public double calculateAverageNumberOfOnlineVehilces(String type);

	public Integer calculateSum(Integer value1,Integer value2);

	public Event findByType(String type);
	
}
