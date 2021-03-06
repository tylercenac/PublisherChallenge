package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.PublisherController;
import com.example.demo.data.PublisherEntity;
import com.example.demo.repository.PublisherRepository;


public class InterviewChallengeApplicationTest extends InterviewChallengeApplicationTests{


	@Autowired
	PublisherController publisherController;


	
	@Test
	public void test1() throws Exception {
		
		assertEquals(publisherController.getPublisherByCode("AbEKUw").getFile(), "s3://some-special-bucket/production/publisher-data/AbEKUw-c0589a07-c8d1-4fed-b97e-4d15f670fd2c");
	}

	@Test
	public void test2() throws Exception {
		
		assertEquals(publisherController.getPublisherByCode("kuziqi").getFile(), null);
	}
	


}
