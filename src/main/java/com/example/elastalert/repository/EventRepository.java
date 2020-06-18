package com.example.elastalert.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.elastalert.model.Event;

@Repository
public interface EventRepository extends ElasticsearchRepository<Event, Long> {

}
