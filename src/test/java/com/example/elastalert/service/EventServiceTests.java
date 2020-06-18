package com.example.elastalert.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import com.example.elastalert.ElastalertApplication;
import com.example.elastalert.model.Event;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElastalertApplication.class)
public class EventServiceTests {

	@Autowired
	private EventService eventService;
	
	@MockBean
	private RedisCacheService redisCacheService;
	
	@MockBean
	private KafkaMessageService kafkaMessageService;
	
	@Captor
	private ArgumentCaptor<Event> redisCacheCaptor;
	
	@Captor
	private ArgumentCaptor<Event> kafkaCaptor;
	
	//@Test
	public void test_CalculateSum_success() {
		Integer sum = eventService.calculateSum(10, 20);
		assertEquals(30, sum.intValue());
	}
	
	@Before
	public void setup() {
		Mockito.doReturn(null).when(redisCacheService).findByName(eq("Foo"));
		Event event1=Event.builder().eventType("type2").onlineVehicles(10).build();
		Event event2=Event.builder().eventType("type2").onlineVehicles(20).build();
		Event event3=Event.builder().eventType("type2").onlineVehicles(30).build();
		
		Mockito.doReturn(Arrays.asList(event1,event2,event3)).when(redisCacheService).findByType(anyString());
		
	}
	
	//@Test
	public void test_createEvent_success() {
		
		//1) whether the event is saved into the elastic  --- satisifed
		Event event=Event.builder().name("Foo").id(30l).eventType("type1").build();
		Event result = eventService.create(event);
		assertEquals("Foo", result.getName());
		assertEquals(30l, result.getId().longValue());
		assertEquals("type1", result.getEventType());
		
		//2) if the event is not in cache it should be save to cache -----
		verify(redisCacheService, times(1)).save(redisCacheCaptor.capture());
		Event cacheParamEvent = redisCacheCaptor.getAllValues().get(0);
		assertEquals("Foo", cacheParamEvent.getName());
		assertEquals(30l, cacheParamEvent.getId().longValue());
		assertEquals("type1", cacheParamEvent.getEventType());
		
		//3) the event should be published to the kafka
		verify(kafkaMessageService, times(1)).publishEvent(kafkaCaptor.capture());
		Event kafkaParamEvent = kafkaCaptor.getAllValues().get(0);
		assertEquals("Foo", kafkaParamEvent.getName());
		assertEquals(30l, kafkaParamEvent.getId().longValue());
		assertEquals("type1", kafkaParamEvent.getEventType());
		
	}
	
	@Test
	public void test_calculateAverageNumberOfOnlineVehilces_sucess() {
		double average= eventService.calculateAverageNumberOfOnlineVehilces("type2");
		assertEquals(20, average,0.0);
	}
	
	
}
