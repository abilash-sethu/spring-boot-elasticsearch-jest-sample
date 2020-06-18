package com.example.elastalert.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastalert.model.Event;
import com.example.elastalert.service.EventService;

@RestController
@RequestMapping("/api")
public class EventController {

	@Autowired
	private EventService eventService;
	
	private Logger log=LoggerFactory.getLogger(EventController.class);

	
	@GetMapping("/event/{type}")
	public Event findByType(@PathVariable String type) {
		log.debug("findByType {} ",type);
		return eventService.findByType(type);
		
	}
	
	
	@PostMapping("/event")
	public void alert(@RequestBody Event event) {
		log.debug("alert posted successfully with payload {}",event);
	}
}
