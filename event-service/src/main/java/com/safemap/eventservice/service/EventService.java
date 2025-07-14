package com.safemap.eventservice.service;

import com.safemap.eventservice.model.Event;
import com.safemap.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public Event create(Event event) {
        return repo.save(event);
    }

    public List<Event> findAll() {
        return repo.findAll();
    }

    public List<Event> findByPlace(String placeId) {
        return repo.findByPlaceId(placeId);
    }

    public Optional<Event> findById(String id) {
        return repo.findById(id);
    }

    public List<Event> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return repo.findByDateTimeBetween(start, end);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
