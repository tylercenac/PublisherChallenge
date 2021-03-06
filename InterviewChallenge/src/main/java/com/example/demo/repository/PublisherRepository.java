package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.PublisherEntity;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherEntity, String>{

	PublisherEntity getPublisherByCode(String code);

}
