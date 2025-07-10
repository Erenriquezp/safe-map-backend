package com.safemap.scheduleservice.service;

import com.safemap.scheduleservice.model.Schedule;
import com.safemap.scheduleservice.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository repo;

    public ScheduleService(ScheduleRepository repo) {
        this.repo = repo;
    }

    public Schedule create(Schedule schedule) {
        return repo.save(schedule);
    }

    public List<Schedule> findAll() {
        return repo.findAll();
    }

    public List<Schedule> findByPlace(String placeId) {
        return repo.findByPlaceId(placeId);
    }

    public Optional<Schedule> findById(String id) {
        return repo.findById(id);
    }

    public Schedule update(String id, Schedule updated) {
        updated.setId(id);
        return repo.save(updated);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
