package com.safemap.scheduleservice.controller;

import com.safemap.scheduleservice.model.Schedule;
import com.safemap.scheduleservice.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(service.create(schedule));
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<Schedule>> getByPlace(@PathVariable String placeId) {
        return ResponseEntity.ok(service.findByPlace(placeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@PathVariable String id,
                                           @RequestBody Schedule schedule) {
        return ResponseEntity.ok(service.update(id, schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
