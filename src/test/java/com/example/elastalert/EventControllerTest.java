package com.example.elastalert;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.elastalert.controller.EventController;
import com.example.elastalert.model.Event;
import com.example.elastalert.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {EventController.class})
public class EventControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EventService eventService;
	
	@Before
	public void setup() {
		Event mockEvent = Event.builder().eventType("type2").build();
		when(eventService.findByType("type2")).thenReturn(mockEvent);
	}

	@Test
	public void test_findByType() throws Exception {
		
		ObjectMapper mapper=new ObjectMapper();
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/event/type2").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Event result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Event.class);
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("type2", result.getEventType());
	}
	
}
