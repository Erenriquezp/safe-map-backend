package com.safemap.safeplaceservice.controller;

import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.service.PlaceService;
import org.springframework.http.ResponseEntity;
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
     * Crear un nuevo lugar seguro.
     */
    @PostMapping
    public ResponseEntity<Place> create(@RequestBody Place place, Principal principal) {
        // Obtener el userId desde el JWT ya validado
        String userId = principal != null ? principal.getName() : "anonymous";
        place.setCreatedBy(userId);
        Place created = service.create(place);
        return ResponseEntity
                .created(URI.create("/places/" + created.getId()))
                .body(created);
    }

    /**
     * Listar todos los lugares.
     */
    @GetMapping
    public ResponseEntity<List<Place>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Obtener un lugar por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Place> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar los datos de un lugar.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Place> update(@PathVariable String id, @RequestBody Place updated) {
        return service.update(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un lugar por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
