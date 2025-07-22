package com.safemap.safeplaceservice.controller;

import com.safemap.safeplaceservice.dto.PlaceResponseDto;
import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    /**
     * Crear un nuevo lugar seguro (usuario autenticado).
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Place> create(@RequestBody Place place, Principal principal) {
        place.setCreatedBy(principal.getName());
        Place created = service.create(place);
        return ResponseEntity
                .created(URI.create("/places/" + created.getId()))
                .body(created);
    }

    /**
     * Listar todos los lugares (público).
     */
    @GetMapping
    public ResponseEntity<List<Place>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Obtener un lugar por ID (público).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Place> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar lugar (solo ADMIN o creador).
     */
    @PreAuthorize("hasRole('ADMIN') or @placeSecurity.isOwner(#id, authentication.name)")
    @PutMapping("/{id}")
    public ResponseEntity<Place> update(@PathVariable String id, @RequestBody Place updated) {
        return service.update(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar lugar (solo ADMIN o creador).
     */
    @PreAuthorize("hasRole('ADMIN') or @placeSecurity.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/with-schedules")
    public ResponseEntity<PlaceResponseDto> getWithSchedules(@PathVariable String id) {
        return service.findWithSchedules(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
