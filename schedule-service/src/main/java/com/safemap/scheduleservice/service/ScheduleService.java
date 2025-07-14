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

    // CREATE
    public Schedule create(Schedule schedule) {
        return repo.save(schedule);
    }

    // READ ALL
    public List<Schedule> findAll() {
        return repo.findAll();
    }

    // READ by ID
    public Optional<Schedule> findById(String id) {
        return repo.findById(id);
    }

    // READ by place
    public List<Schedule> findByPlace(String placeId) {
        return repo.findByPlaceId(placeId);
    }

    // UPDATE
    public Optional<Schedule> update(String id, Schedule updated) {
        return repo.findById(id).map(existing -> {
            existing.setDayOfWeek(updated.getDayOfWeek());
            existing.setStartTime(updated.getStartTime());
            existing.setEndTime(updated.getEndTime());
            return repo.save(existing);
        });
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }
}
