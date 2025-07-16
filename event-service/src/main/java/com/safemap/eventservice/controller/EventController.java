package com.safemap.eventservice.controller;

import com.safemap.eventservice.model.Event;
import com.safemap.eventservice.service.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    /**
     * Crear un nuevo evento (usuario autenticado).
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event, Principal principal) {
        event.setReportedBy(principal != null ? principal.getName() : "anonymous"  ); // userId desde el token
        Event created = service.create(event);
        return ResponseEntity.created(URI.create("/events/" + created.getId())).body(created);
    }

    /**
     * Listar todos los eventos (público).
     */
    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Obtener un evento por ID (público).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Listar eventos por lugar (público).
     */
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<Event>> getByPlace(@PathVariable String placeId) {
        return ResponseEntity.ok(service.findByPlace(placeId));
    }

    /**
     * Buscar eventos por rango de fechas (público).
     */
    @GetMapping("/between")
    public ResponseEntity<List<Event>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(service.findByDateRange(start, end));
    }

    /**
     * Eliminar evento por ID (solo ADMIN).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
