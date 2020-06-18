package com.example.elastalert.service.impl;

import java.util.IntSummaryStatistics;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.elastalert.model.Event;
import com.example.elastalert.repository.EventRepository;
import com.example.elastalert.service.EventService;
import com.example.elastalert.service.KafkaMessageService;
import com.example.elastalert.service.RedisCacheService;


@Service
@Transactional
public class EventServiceImpl implements EventService {

	private Logger log=LoggerFactory.getLogger(EventServiceImpl.class);
	
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private RedisCacheService redisCacheService; //mock

	@Autowired
	private KafkaMessageService kafkaMessageService; //mock

	@Override
	public Event create(Event event) {
		Event result = eventRepository.save(event);
		Event cachedEvent = redisCacheService.findByName(event.getName());
		if (cachedEvent == null) {
			log.debug("Event does not exist in cache, creating one {} ",event.getName());
			redisCacheService.save(result);
		}
		kafkaMessageService.publishEvent(result);
		return result;
	}
	
	@Override
	public Event save(Event event) {
		Event result = eventRepository.save(event);
		return result;
	}
	
	
	

	public double calculateAverageNumberOfOnlineVehilces(String type) {
		List<Event> events = redisCacheService.findByType(type);
		log.debug("Events {} ",events);
		IntSummaryStatistics stat = events.stream()
				.mapToInt(Event::getOnlineVehicles)
				.summaryStatistics();
		return stat.getAverage();
	}
	
	
	
	public Integer calculateSum(Integer value1,Integer value2) {
		return value1+value2;
	}

	@Override
	public Event findByType(String type) {
		return null;
	}

}
