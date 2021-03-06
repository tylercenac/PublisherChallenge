package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.PublisherEntity;
import com.example.demo.repository.PublisherRepository;

@Service
public class PublisherSerivce {
	
	@Autowired
	PublisherRepository publisherRepository;
	
	public List<PublisherEntity> getAllPublishers(){
		return (List<PublisherEntity>) publisherRepository.findAll();
	}
	

	public Iterable<PublisherEntity> savePublishers(List<PublisherEntity> publishers) {
		return publisherRepository.saveAll(publishers);
		
	}


	public PublisherEntity getPublisherByCode(String code) {
		
		return publisherRepository.getPublisherByCode(code);
	}


	public PublisherEntity savePublisher(PublisherEntity publisherEntity) {
		return publisherRepository.save(publisherEntity);
	}
	
	
	
	
}
