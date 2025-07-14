package com.safemap.safeplaceservice.service;

import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository repo;

    public PlaceService(PlaceRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public Place create(Place place) {
        place.setCreatedAt(Instant.now());
        place.setUpdatedAt(Instant.now());
        place.setAverageRating(0.0); // default
        return repo.save(place);
    }

    // READ ALL
    public List<Place> findAll() {
        return repo.findAll();
    }

    // READ BY ID
    public Optional<Place> findById(String id) {
        return repo.findById(id);
    }

    // UPDATE
    public Optional<Place> update(String id, Place updated) {
        return repo.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setLatitude(updated.getLatitude());
            existing.setLongitude(updated.getLongitude());
            existing.setUpdatedAt(Instant.now());
            return repo.save(existing);
        });
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // (Opcional) Filtrar por usuario creador
    public List<Place> findByCreator(String userId) {
        return repo.findAll().stream()
                .filter(p -> userId.equals(p.getCreatedBy()))
                .toList();
    }
}
