package com.example.elastalert;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.elastalert.model.Event;
import com.example.elastalert.service.EventService;
import com.example.elastalert.service.KafkaMessageService;
import com.example.elastalert.service.RedisCacheService;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { TestConfiguration.class })
@SpringBootTest(classes = ElastalertApplication.class)
public class EventServiceTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private KafkaMessageService kafkaMessageService;

	@Captor
	ArgumentCaptor<Event> eventCacheCaptor;

	@Captor
	ArgumentCaptor<Event> eventKafkaMessageCaptor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(null).when(redisCacheService).findByName(eq("Foo"));
		Event event1 = Event.builder().eventType("Type1").onlineVehicles(30).build();
		Event event2 = Event.builder().eventType("Type1").onlineVehicles(10).build();
		Event event3 = Event.builder().eventType("Type1").onlineVehicles(20).build();

		Mockito.doReturn(Arrays.asList(event1, event2, event3)).when(redisCacheService).findByType(anyString());

	}

	@Test
	public void test_createEvent() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 5);
		cal.add(Calendar.MINUTE, 33);
		Event event = Event.builder().creationDate(cal.getTime()).id(22l).onlineVehicles(5)
				.requiredNumberOnlineVehicles(8).name("Foo").timestamp(cal.getTime()).onlineEmployees(3)
				.requiredNumberOnlineEmployees(2).build();
		event = eventService.create(event);
		verify(redisCacheService, times(1)).save(eventCacheCaptor.capture());
		verify(kafkaMessageService, times(1)).publishEvent(eventKafkaMessageCaptor.capture());

		assertEquals(22l, event.getId().longValue());

		Event cacheArgEvet = eventCacheCaptor.getAllValues().get(0);
		assertEquals(22l, cacheArgEvet.getId().longValue());
		assertEquals("Foo", cacheArgEvet.getName());

		Event kafkaArgEvet = eventKafkaMessageCaptor.getAllValues().get(0);
		assertEquals(22l, kafkaArgEvet.getId().longValue());
		assertEquals("Foo", kafkaArgEvet.getName());

	}

	@Test
	public void test_calculateAverageNumberOfOnlineVehilces() {
		double average = eventService.calculateAverageNumberOfOnlineVehilces("Type1");
		assertEquals(20, average, 0.0);
	}

	@Test
	public void test_CalculateSum() {
		Integer sum = eventService.calculateSum(10, 20);
		assertEquals(30, sum.intValue());
	}

}
