package com.safemap.scheduleservice.controller;

import com.safemap.scheduleservice.model.Schedule;
import com.safemap.scheduleservice.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    /**
     * Crear un nuevo horario seguro.
     */
    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {
        Schedule created = service.create(schedule);
        return ResponseEntity
                .created(URI.create("/schedules/" + created.getId()))
                .body(created);
    }

    /**
     * Listar todos los horarios.
     */
    @GetMapping
    public ResponseEntity<List<Schedule>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Obtener horarios por lugar.
     */
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<Schedule>> getByPlace(@PathVariable String placeId) {
        return ResponseEntity.ok(service.findByPlace(placeId));
    }

    /**
     * Obtener un horario por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar un horario existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@PathVariable String id, @RequestBody Schedule updated) {
        return service.update(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un horario por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
