package com.safemap.safeplaceservice.controller;

import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Place> create(@RequestBody Place place) {
        return ResponseEntity.ok(service.create(place));
    }

    @GetMapping
    public ResponseEntity<List<Place>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> update(@PathVariable String id, @RequestBody Place place) {
        return ResponseEntity.ok(service.update(id, place));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
