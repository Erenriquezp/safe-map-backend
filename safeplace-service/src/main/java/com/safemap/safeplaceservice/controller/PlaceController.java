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
    /**
     * Listar lugares seguros con rating mínimo (público).
     */
    @GetMapping("/safe")
    public ResponseEntity<List<Place>> getSafePlaces(
            @RequestParam(defaultValue = "4.0") double minRating) {
        List<Place> safePlaces = service.findSafePlaces(minRating);
        return ResponseEntity.ok(safePlaces);
    }

    /**
     * Crear múltiples lugares seguros (bulk insert) - solo ADMIN.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<Place>> createBulk(@RequestBody List<Place> places, Principal principal) {
        String creatorId = principal.getName();
        // Asignar el creador a cada lugar
        places.forEach(place -> place.setCreatedBy(creatorId));
        List<Place> createdPlaces = service.createAll(places);
        return ResponseEntity.ok(createdPlaces);
    }

}
