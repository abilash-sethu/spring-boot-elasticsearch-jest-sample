package com.example.elastalert.service;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.elastalert.ElastalertApplication;
import com.example.elastalert.model.Event;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElastalertApplication.class)
public class EventTest {

	@Autowired
	private EventService eventService;

	@Test
	public void test_createEventOnlineVehicles_success() {
		Calendar now=Calendar.getInstance();
		now.add(Calendar.MINUTE, -30);
		Random random = new Random();
		Long id = Long.valueOf(random.nextInt(500));
		Event event = Event.builder().name("InsufficientNumberOfOnlineEmployees").region("Oceana").id(id).eventType("vehicle")
				.creationDate(new Date()).timestamp(now.getTime()).onlineVehicles(5).requiredNumberOnlineVehicles(10).build();
		Event result = eventService.save(event);
		assertEquals("InsufficientNumberOfOnlineVehicles", result.getName());
		assertEquals(id, result.getId());
		assertEquals("vehicle", result.getEventType());
		System.out.println("##################### ID IS "+id+"###########################");
	}
	
	//@Test
	public void test_createEventOnlineEmployees_success() {
		Random random = new Random();
		Long id = Long.valueOf(random.nextInt(500));
		Event event = Event.builder().name("InsufficientNumberOfOnlineEmployees").id(id).eventType("type-"+id)
				.creationDate(new Date()).timestamp(new Date()).onlineEmployees(5).requiredNumberOnlineEmployees(10).build();
		Event result = eventService.save(event);
		assertEquals("InsufficientNumberOfOnlineEmployees", result.getName());
		assertEquals(id, result.getId());
		assertEquals("type2", result.getEventType());
		System.out.println("##################### ID IS "+id+"###########################");

	}
}
