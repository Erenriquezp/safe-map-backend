package com.safemap.eventservice.repository;

import com.safemap.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByPlaceId(String placeId);
    List<Event> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
