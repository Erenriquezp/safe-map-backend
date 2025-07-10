package com.safemap.safeplaceservice.service;

import com.safemap.safeplaceservice.model.Place;
import com.safemap.safeplaceservice.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    private final PlaceRepository repo;

    public PlaceService(PlaceRepository repo) {
        this.repo = repo;
    }

    public Place create(Place place) {
        return repo.save(place);
    }

    public List<Place> findAll() {
        return repo.findAll();
    }

    public Optional<Place> findById(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public Place update(String id, Place updated) {
        updated.setId(id);
        return repo.save(updated);
    }
}
